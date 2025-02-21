(ns example.components.otpcomponent
  (:require
   ["react-native" :as rn]
   [example.utils.strings :as strings]))

(def styles
  {:container {:justify-content :space-evenly :align-items :center :margin-top 15  :height 250 :margin-left 20}
   :input-container {:justify-content :flex-start :align-items :flex-start :margin-top 20}
   :input-label {:color "#000" :font-weight 600 :font-size 12}
   :text-input {:width "100%" :height 50 :border-bottom-width 1
                :border-radius 8 :border-color "#ccc"
                :font-size 24 :text-align "center"}
   :button {:margin-top 20 :width 200 :height 50 :border-radius 8
            :justify-content :center :align-items :center :padding-bottom 15}
   :submit-button {:background-color "#87CEEB"}
   :submit-text {:color "white" :font-size 14 :font-weight "bold"}
   :resend-button {:background-color "#fff" :border-radius 8 :border-width 0.5
                   :width 200 :height 50 :justify-content :center
                   :align-items :center :border-color "#87CEEB"}
   :resend-text {:color "#4682B4" :font-size 14 :font-weight "medium"}
   :back-button {:background-color "#fff" :border-radius 8 :border-width 0.5
                 :width 250 :height 50 :justify-content :center
                 :align-items :center :border-color "#87CEEB"}})

(defn handle-submit-otp [navigation user-id otp user-data]
  (js/console.log "Submitting OTP for user-id:" user-id "OTP:" @otp)
  (def original-console-error (.-error js/console))
  (set! (.-error js/console)
        (fn [& args]
          (let [error-message (str (first args))]
            (when-not (re-find #"cljs\\$core\\$IFn\\$_invoke\\$arity\\$0" error-message)
              (apply original-console-error args)))))
  ((-> navigation (.navigate "Information"
                             (assoc user-data :email (:email user-data)))))
  (.catch (fn [error]
            (js/console.error "Error during activation:" error)
            (js/alert (strings/texts :activatiofail)))))

(defn otp-input [otp]
  [:> rn/View {:style (:input-container styles)}
   [:> rn/Text {:style (:input-label styles)} (strings/texts :smscode)]
   [:> rn/TextInput {:style (:text-input styles)
                     :keyboard-type "numeric"
                     :max-length 6
                     :value @otp
                     :on-change-text #(reset! otp %)}]])

(defn otp-buttons [navigation user-id otp password verbal-password email first-name last-name mobile-phone]
  [:> rn/View {:style (:container styles)}
   [:> rn/Pressable {:style (merge (:button styles) (:submit-button styles))
                     :on-press #(handle-submit-otp navigation user-id otp {:password password :verbal-code verbal-password :email email :first_name first-name :last_name last-name :phone mobile-phone :ga-session-id "qwfcvvf12dez" :ga-client-id "qwfcvvf12dez" :plan "manage_records_silver"})}
    [:> rn/Text {:style (:submit-text styles)} (strings/texts :submitcode)]]

   [:> rn/Pressable {:style (:resend-button styles)
                     :on-press #(js/alert (strings/texts :resendingotp))}
    [:> rn/Text {:style (:resend-text styles)} (strings/texts :resendcode)]]

   [:> rn/Pressable {:style (:back-button styles)
                     :on-press #(-> navigation (.replace "SignIn"))}
    [:> rn/Text {:style (:resend-text styles)} (strings/texts :backsignin)]]])
