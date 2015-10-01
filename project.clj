(defproject gw2-blacklion-trends "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "1.1.2"]
                 [cheshire "5.5.0"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.xerial/sqlite-jdbc "3.8.10.1"]]
  :main gw2-blacklion-trends.core
  :profiles {:uberjar {:aot :all}})
