# AndroidSerialSQL

This project intends to solve the problem of concurrent write attempts from different threads to an Android SQLite database. 

## The Problem

Here's a [blog post explaining the issue](http://touchlabblog.tumblr.com/post/24474398246/android-sqlite-locking), along with a choice quote:

> If you try to write to the database from actual distinct connections at the same time, one will fail.  It will not wait till the first is done and then write.  It will simply not write your change.  Worse, if you don’t call the right version of insert/update on the SQLiteDatabase, you won’t get an exception.  You’ll just get a message in your LogCat, and that will be it.

This goes farther than the singleton pattern of only getting one database object (or one writable database object) and implements a blocking queue with a thread pool executor, where the thread pool has a max size of one. This means that there will be a single thread that handles database write operations, and it will work through the backlog of requests that exists in the queue.

In order to accomplish this, we cut off access to a writable version of the database outside of a couple abstract runnables, which are intended to be added to the queue. `WriterTask` and `UpgradeRunnable` are those specialized runnables, both of them hold a reference to a database, and have ways of grabbing a reference to the writable db. There are a couple of data structures dedicated to handling the database (or databases), and these special runnables.

## Using this lib

This is a standard Android library project, so if you're using Eclipse, or are familiar with using Android library projects, just do what you normally do. I should probably turn this into a jar at some point, but I'm lazy, and may not get around to it. Plus, if I did that, I'd want to make sure that it was polished enough to submit to Maven Central and all that jazz, but that's not where things are right now. 

**Use at your own risk!** Right now, this is more of a good starting point, and something to look at as a reference. Don't pull it into your project unless you plan on forking it and fixing problems as they appear. 

If you're using Gradle, you can do the following in the parent directory, or wherever you want to put your libraries:

    git submodule add git@github.com:emil10001/AndroidSerialSQL.git

In your project's `settings.gradle`:

    include ':YourApp', ':AndroidSerialSQL'

In your app's `build.gradle`:

    dependencies {
        compile project(':AndroidSerialSQL')
    }

## Usage

There are a few steps to get this up and running. It should all be fairly straght-forward.

### 1

Create a defenition of your database.

    DefineDB myDB = new DefineDB("myDB", 1);
    myDB.setTableDefenition("items", 
      "create table items "
      + "( _id integer primary key autoincrement, "
      + "item text);");

### 2

Use the defenition to open/create the database, and store it in a data structure for use.

    AccessDB.addDB(context, myDB);

### 3

Insert an item into your database.

    AccessDB.addWriteTask(new WriterTask("myDB", callback) {
    @Override
        public void run() {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put("item", "five");
                db.insert(ITEMS, null, values);
                db.setTransactionSuccessful();
            } catch (Exception ex) {
                Log.e(TAG, "failed to insert", ex);
            } finally {
                db.endTransaction();
            }
            callback.run();
        }
    });

### 4

Retrieve things from the database.

    AccessDB.getReadableDB("myDB").query("items", null, 
      null, null, null, null, null);

### 5

Handle upgrades by adding to the database defenition.

    myDB.setVersionUpgrade(2, new UpgradeRunnable() {
        @Override
        public void run() {
            db.execSQL("create table two"
            + "( _id integer primary key autoincrement, "
            + "different_thing text);");
        }
    });

## Sample implementation

Check out the [sample branch](https://github.com/emil10001/AndroidSerialSQL/tree/sample) for a working app that implements this library.

By <a href="https://plus.google.com/u/0/110693175237378228684?rel=author">E John Feig</a>
