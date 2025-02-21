(ns example.service.auth
  (:require
   [example.utils.APIManager :as apiConstants]))
(def token "eyJhbGciOiJSU0EtT0FFUCIsImVuYyI6IkExMjhDQkMtSFMyNTYifQ.bv6YwIKeBD32smZH0ByJNZft99irAXbMyPuzMsnxV5XUeXee2jV23ffuwO9H5G6VYxoX8E4M1M5UdJ77Vc8ccsYSbHxqHba7j3c3EUR47lfWmB2yV9G0H9-GhtFbw-OvaUrIpiaKAx4jKbrO_ad2FOl50kTX1IIGIAIHhnmGOkbDr-jUKJAFKBBVDTq_xiHu_kqVU5yzF9wPjMKe20nOSheBT6hggk9bM1Mun9Saw_lo_K4hlXun7uDcitjFxZmebTv_mjJ-jItzx7E9o3hZGMceCxJz0nD-z9rQGrIUf5s4vvCAEuv4y-b4_wo2jrSrnmyyLVOQx0xQs6KiX_nIqw.PASuCufylpMMXgALYDxn9A.8CeFUtxA3SxlSZHzJ46p6YxgHKF_kMT801pyTqmdXUnEPrLgOIow4nhcBNLjeUQTqETGTfg3RD4kZdLVIP6V5t-SoyTdYodU7f4uJlzxPyORsyd3QBXHpexKlXFeg72H0JfsdsMjWcSXSKb4Ldmbw0qFm5Wki9QvmO67IN4qhfY7Ml-bY3Jinvd-HifvhqJu.Q75vTO_hzyb6Xffz4U2QlA")
(defn extract-key-value [response]
  response) 

(defn validate-user [email phone]
  (let [payload {:email email :phone phone}]
    (println "Validating user with payload:" payload)
    (-> (apiConstants/api-request "POST" :validate-user payload)
        (.then (fn [response]
                 (let [formatted-response (extract-key-value response)]
                   (js/console.log "Formatted Validate User Response:" (clj->js formatted-response))
                   formatted-response)))
        (.catch (fn [error]
                  (js/console.error "Error occurred while validating user:" error)
                  error)))))

(defn check-contact [type value]
  (let [payload {:type type :value value}]
    (println "Checking contact with payload:" payload)
    (-> (apiConstants/api-request "POST" :check-contact payload)
        (.then (fn [response]
                 (let [formatted-response (extract-key-value response)]
                   (js/console.log "Formatted Check Contact Response:" (clj->js formatted-response))
                   formatted-response)))
        (.catch (fn [error]
                  (js/console.error "Error occurred while checking contact:" error)
                  error)))))

(defn activate-contact [id code]
  (let [payload {:id id :code code}]
    (println "Activating contact with payload:" payload)
    (-> (apiConstants/api-request "POST" :activate-contact payload)
        (.then (fn [response]
                 (let [formatted-response (extract-key-value response)]
                   (js/console.log "Formatted Activate Contact Response:" (clj->js formatted-response))
                   formatted-response)))
        (.catch (fn [error]
                  (js/console.error "Error occurred while activating contact:" error)
                  (throw error)))
        (.finally #(println "Activation request completed.")))))

(defn register-user [user-data]
  (println "Registering user with payload:" user-data)
  (-> (apiConstants/api-request "POST" :register-user user-data)
      (.then (fn [response]
               (let [formatted-response (extract-key-value response)]
                 (js/console.log "Formatted Register User Response:" (clj->js formatted-response))
                 formatted-response)))
      (.catch (fn [error]
                (js/console.error "Error occurred while registering user:" error)
                error))))

(defn generate-seed []
  (println "Generating seed...")
  (-> (apiConstants/api-request "GET" :generate-seed nil {:headers {"Authorization" (str "Bearer " token)}})
      (.then (fn [response]
               (let [formatted-response (extract-key-value response)]
                 (js/console.log "Formatted Generate Seed Response:" (clj->js formatted-response))
                 formatted-response)))
      (.catch (fn [error]
                (js/console.error "Error occurred while generating seed:" error)
                error))))

(defn submit-seed []
  (let [payload {:seed-vec ["female" "major" "unaware" "lava" "strike" "warfare"
                            "fire" "quiz" "select" "attitude" "zero" "cart"
                            "current" "secret" "carry" "stomach" "answer"
                            "ethics" "round" "topic" "depth" "knife" "tool"
                            "endorse"]}]
    (println "Submitting seed with payload:" payload)
    (-> (apiConstants/api-request "POST" :submit-seed payload {:headers {"Authorization" (str "Bearer " token)}})
        (.then (fn [response]
                 (let [formatted-response (extract-key-value response)]
                   (js/console.log "Seed submitted successfully, response:" formatted-response)
                   formatted-response)))
        (.catch (fn [error]
                  (js/console.error "Error occurred while submitting seed:" error)
                  error)))))


(defn poll-new-stripe-customer []
  (println "Polling for new Stripe customers...")
  (-> (apiConstants/api-request "GET" :poll-stripe-customer)
      (.then (fn [response]
               (let [formatted-response (extract-key-value response)]
                 (js/console.log "New Stripe customer found, response:" formatted-response)
                 formatted-response)))
      (.catch (fn [error]
                (if (instance? js/Error error)
                  (js/console.error "Error occurred while polling for new Stripe customers:" error)
                  (js/console.error "Unknown error occurred during polling:" error))
                (if (instance? js/Error error)
                  {:type "unknown-exception" :class "clojure.lang.ExceptionInfo"})))))
