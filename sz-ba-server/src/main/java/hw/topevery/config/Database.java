package hw.topevery.config;

import hw.topevery.framework.db.DbExecute;

/**
 * @author bingxin.xu
 */
public class Database {

    public static final DbExecute dbDatabase;

    static {
        dbDatabase = DbExecute.getInstance("dbDatabase");
    }

    public static void transactionScope(Runnable runnable) {
        Database.dbDatabase.transactionScope(runnable);
    }
}
