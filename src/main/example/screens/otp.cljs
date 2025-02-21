(ns example.screens.otp
  (:require
   ["react-native" :as rn]
   [example.components.otpcomponent :refer [otp-buttons otp-input]]
   [example.service.auth :refer [register-user]]
   [example.utils.async-storage :refer [save-to-storage]]
   [reagent.core :as r]
   [example.utils.strings :as strings]))

(defn phone-activation [id code]
  (js/console.log "Activating user")
  (let [payload {:id id :code code}]
    (-> (phone-activation id code)
        (.then (fn [response]
                 (if (:error_message response)
                   (do
                     (js/alert (:error_message response))
                     (throw (js/Error (:error_message response)))))))
        (.catch (fn [error]
                  (js/console.error "Error occurred while activating contact:" error)
                  (js/alert (strings/texts :failedactivationcontact))
                  error)))))


(defn register [navigation user-data]
  (-> (register-user user-data)
      (.then (fn [response]
               (let [error-message (:error_message response)
                     status (:status response)]
                 (if error-message
                   (do
                     (js/alert error-message))
                   (if (= status "success")
                     (do
                       (js/console.log "Registration successful")
                       (js/alert (strings/texts :userregistersuccess))
                       (phone-activation (:id response) (:code response))
                       (save-to-storage "token" (:token response))

                       (-> navigation (.replace "SignIn")))
                     (js/alert (strings/texts :errorregistration)))))))
      (.catch (fn [error]
                (js/console.error "Error during registration:" error)
                (js/alert (strings/texts :registrationfailederror))))))

(defn otp-screen [^js props]
  (r/with-let [otp (r/atom "")
               params (-> props .-route .-params)]
    (if (nil? params)
      (do
        (js/console.warn "Params are null or undefined")
        [:> rn/Text {:style {:color "red"}} (strings/texts :paramsmissing)])
      (do
        #_{:clj-kondo/ignore [:unused-binding]}
        (let [user-id (:user-id params)
              first-name (:first-name params)
              last-name (:last-name params)
              email (:email params)
              mobile-phone (:mobile-phone params)
              backup-mobile-phone (:backup-mobile-phone params)
              password (:password params)
              password-confirmation (:password-confirmation params)
              verbal-password (:verbal-password params)
              confirm-verbal-password (:confirm-verbal-password params)]

          [:> rn/SafeAreaView {:style {:flex 1 :background-color "#fff"}}
           [:> rn/View {:style {:height 70
                                :width "100%"
                                :justify-content :flex-start
                                :align-items :center
                                :background-color "white"
                                :border-bottom-width 1
                                :border-color "#e8e8e8"
                                :margin-top 25}}
            [:> rn/View {:style {:flex-direction "row"
                                 :align-items :center
                                 :margin-top 10
                                 :justify-content :flex-start
                                 :width "100%"}}
             [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "SignUp"))}
              [:> rn/Image {:source (js/require "../assets/back.png")
                            :style {:width 30 :height 25 :margin-bottom 10 :margin-right 15 :margin-left 10}}]]
             [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                           :style {:width 50 :height 50 :margin-bottom 10}}]
             [:> rn/Text {:style {:font-size 24 :color "#000000" :margin-left 10}} (strings/texts :app-title)]]]
           [:> rn/View {:style {:flex 1  :margin 20}}


            [:> rn/View
             {:style {:justify-content :flex-start :align-items :flex-start :margin-top 30}}
             [:> rn/Text {:style {:color "#000" :font-size 20 :font-weight 600}}
              (strings/texts :codeviatext)]]

            [:> rn/View
             {:style {:justify-content :flex-start :align-items :flex-start :margin-top 20}}
             [:> rn/Text {:style {:color "#000" :font-size 14}}
              (strings/texts :codevianumber)]]

            [otp-input otp]


            [otp-buttons (.-navigation props) user-id otp password verbal-password email first-name last-name mobile-phone]]])))))
