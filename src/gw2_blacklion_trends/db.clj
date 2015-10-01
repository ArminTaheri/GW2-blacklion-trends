(ns gw2-blacklion-trends.db
  (:require [clojure.java.jdbc :as sql]))

(def dbspec (atom {:classname "org.sqlite.JDBC"
                   :subprotocol "sqlite"
                   :subname "gw2_data.sq3"}))

(defn setdbfile [dbfile]
  (do
    (swap! dbspec
           #(merge {:classname "org.sqlite.JDBC"
                    :subprotocol "sqlite"
                    :subname dbfile} %))
    (let [dbIsInValid (or (not (.exists (java.io.File. (@dbspec :subname))))
                          (empty?
                            (sql/query @dbspec "SELECT name FROM sqlite_master WHERE type='table' AND name='GW2BuyTable';"))
                          (empty?
                            (sql/query @dbspec "SELECT name FROM sqlite_master WHERE type='table' AND name='GW2BuyTable';")))]
      (if dbIsInValid
        (sql/db-do-commands
          @dbspec
          (sql/create-table-ddl
            :GW2BuyTable
            [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
            [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
            [:item_id "INTEGER"]
            [:price "INTEGER"]
            [:quantity "INTEGER"])
          (sql/create-table-ddl
            :GW2SellTable
            [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
            [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
            [:item_id "INTEGER"]
            [:price "INTEGER"]
            [:quantity "INTEGER"]))))))

(defn insertitem [table row]
  (sql/insert! @dbspec table row))
