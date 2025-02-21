(ns example.utils.APIManager)

(def base-url "https://app.redkangaroo.us/")

(def endpoints
  {:validate-coupon "join/validateCoupon"
   :validate-user "join/validateUser"
   :check-contact "join/checkContact"
   :activate-contact "join/activateContact"
   :register-user "join/registerUser"
   :generate-seed "seed/generateSeed"
   :submit-seed "join/submitSeed"
   :poll-new-stripe-customer "stripe/pollNewStripeCustomer"
   :consumer-done "index.html#/consumer/done"
   :phone-activation " index.html#/consumer/phone-activation"
   :Signinauth "/auth/authUser"
   :Signincode "/signin-code"})

;; Get full URL for the endpoint
(defn get-full-url [endpoint]
  (let [endpoint-path (get endpoints endpoint)]
    (if endpoint-path
      (str base-url endpoint-path)
      (throw (js/Error. (str "Endpoint not found for: " endpoint))))))

;; Function for API requests (GET and POST)
(defn api-request [method endpoint data]
  (let [url (get-full-url endpoint)
        options (case method
                  "POST" {:method "POST"
                          :headers {"Content-Type" "application/json"}
                          :body (js/JSON.stringify (clj->js data))}
                  "GET"  {:method "GET"}
                  (throw (js/Error. (str "Unsupported HTTP method: " method))))]

    (-> (js/fetch url (clj->js options))
        (.then (fn [response]
                 (.json response)))
        (.then (fn [json] (js->clj json :keywordize-keys true)))
        (.catch (fn [error]
                  (js/console.error "API Request Failed:" error))))))