(ns example.utils.async-storage
  (:require ["@react-native-async-storage/async-storage" :as AsyncStorage]))

(defn save-to-storage [key value]
  (-> (AsyncStorage/setItem key (js/JSON.stringify value))
      (.then (fn [] (js/console.log (str "Saved " key " to AsyncStorage."))))
      (.catch (fn [error] (js/console.error "Error saving data:" error)))))

(defn get-from-storage [key]
  (-> (AsyncStorage/getItem key)
      (.then (fn [value]
               (if value
                 (js/JSON.parse value)
                 nil)))
      (.catch (fn [error]
                (js/console.error "Error retrieving data:" error)
                nil))))

(defn remove-from-storage [key]
  (-> (AsyncStorage/removeItem key)
      (.then (fn [] (js/console.log (str "Removed " key " from AsyncStorage."))))
      (.catch (fn [error] (js/console.error "Error removing data:" error)))))
