(ns example.service.api
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async :refer [go <!]]))

(defn request
  "Handles GET and POST requests dynamically based on the method."
  ([url method params] (request url method params {}))
  ([url method params headers]
   (go
     (try
       (let [response (cond
                        (= method :get) (http/get url {:query-params params :headers headers})
                        (= method :post) (http/post url {:json-params params :headers headers})
                        :else (throw (js/Error. "Unsupported HTTP method")))
             body (:body (<! response))]
         (if body
           body
           (throw (js/Error. "No data in response"))))
       (catch js/Error e
         (println "Error during request:" (str e))
         nil)))))
