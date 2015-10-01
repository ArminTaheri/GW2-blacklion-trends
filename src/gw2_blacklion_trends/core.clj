(ns gw2-blacklion-trends.core
  (:gen-class)
  (:require (gw2-blacklion-trends [db :as db])
            (clj-http [client :as client])
            (cheshire [core :refer :all])))

(def blacklion-api "https://api.guildwars2.com/v2/commerce/listings")

(defn- store-gw2-listings [num-samples]
  (let [get-listings
        (fn [id] (clojure.walk/keywordize-keys
                   (first (parse-string ((client/get blacklion-api
                                                     {:query-params {:ids id}}) :body)))))
        item-numbers
        (parse-string ((client/get blacklion-api) :body))
        rect-num-samples
        (min num-samples (count item-numbers))
        get-db-entries
        (fn [id] (let [gw2-req (get-listings id)
                       buys (gw2-req :buys)
                       sells (gw2-req :sells)
                       listing-to-entry
                       (fn [{:keys [unit_price quantity]}]
                         {:item_id id
                          :price unit_price
                          :quantity quantity})]
                   {:buys  (map listing-to-entry buys)
                    :sells (map listing-to-entry sells)}))]
    (println (str "Sampling Guild Wars 2 trading post data with "
                  rect-num-samples
                  " samples."))
    (doseq [entry (take rect-num-samples
                        (map #(try (get-db-entries %)
                                   (catch Exception e nil))
                             (shuffle item-numbers)))]
      (if (empty? (entry :buys))
        nil
        (doseq [row (entry :buys)]
                (db/insertitem :GW2BuyTable row)))
      (if (empty? (entry :sells))
        nil
        (doseq [row (entry :sells)]
                (db/insertitem :GW2SellTable row))))))



(defn -main [& args]
  (let [parsed-input (read-string (second args))
        num-samples (if (and (integer? parsed-input) (> parsed-input 0))
                      parsed-input
                      100)]
    (db/setdbfile (first args))
    (time (store-gw2-listings num-samples))))