(ns example.screens.sign-in
  (:require
   [example.widgets :refer [input-field password-field]]
   ["react-native" :as rn]
   [reagent.core :as r]
   [example.service.api :refer [request]]
   [cljs.core.async :as async :refer [go <!]]
   [example.utils.APIManager :refer [get-full-url]]
   [example.utils.strings :as strings]))

(def styles
  (.create rn/StyleSheet
            (clj->js  {:container {:flex 1 :padding 20 :backgroundColor "white" :justifyContent "center"}
                 :logoContainer {:alignItems "center" :marginBottom 30}
                 :logo {:width 50 :height 50 :marginRight 10}
                 :title {:fontSize 24 :fontWeight "500"}
                 :buttonContainer {:alignItems "center" :gap 8}
                 :primaryButton {:backgroundColor "#87CEEB"
                                 :borderRadius 10
                                 :width 150
                                 :height 50
                                 :alignItems "center"
                                 :justifyContent "center"}
                 :primaryButtonText {:color "#fff"}
                 :secondaryButton {:backgroundColor "#ffffff"
                                   :borderColor "#87CEEB"
                                   :borderRadius 10
                                   :width 150
                                   :height 50
                                   :borderWidth 0.5
                                   :alignItems "center"
                                   :justifyContent "center"}
                 :secondaryButtonText {:color "#4682B4"}
                 :linksContainer {:alignItems "center"
                                  :marginTop 20
                                  :gap 8
                                  :marginBottom 130}
                 :linkText {:color "#87CEEB"}})))

(defn sign-in [^js props]
  (r/with-let [email (r/atom "")
               password (r/atom "")
               show-password? (r/atom false)
               email-value (r/track deref email)
               password-value (r/track deref password)]

    (defn handle-sign-in []
      (let [auth-url (get-full-url :Signinauth)
            code-url (get-full-url :Signincode)
            payload {:email @email-value
                     :password @password-value}]

        (go
          (let [auth-response (async/<! (request auth-url :post payload))
                code-response (async/<! (request code-url :post payload))]
            (cond
              (:check-contact? auth-response)
              (-> props .-navigation (.navigate "OTP"))

              (:activation-code-id code-response)
              (-> props .-navigation (.navigate "OTP"
                                                (clj->js {:activation-code-id (:activation-code-id code-response)})))

              :else
              (-> props .-navigation (.navigate "Plans")))))))

    [:> rn/View {:style (.-container styles)}

     ;; Logo Section
     [:> rn/View {:style (.-logoContainer styles)}
      [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                    :style  (.-logo styles)}]
      [:> rn/Text {:style (.-title styles)} (strings/texts :signintitle)]]

     ;; Input Fields
     [input-field {:label "Email or Mobile Number"
                   :placeholder "Enter your email or phone"
                   :value @email-value
                   :on-change #(reset! email %)}]

     [password-field {:label "Password"
                      :placeholder "Enter your password"
                      :value @password-value
                      :on-change #(reset! password %)
                      :show-password? @show-password?
                      :toggle-password #(swap! show-password? not)}]

     ;; Buttons
     [:> rn/View {:style (.-buttonContainer styles)}
      [:> rn/Pressable {:on-press handle-sign-in
                        :style (.-primaryButton styles)}
       [:> rn/Text {:style (.-primaryButtonText styles)} (strings/texts :signintitle)]]

      [:> rn/Pressable {:on-press #(-> props .-navigation (.navigate "Plans"))
                        :style (.-secondaryButton styles)}
       [:> rn/Text {:style (.-secondaryButtonText styles)} (strings/texts :signmeup)]]]

     ;; Links
     [:> rn/View {:style (.-linksContainer styles)}
      [:> rn/Pressable [:> rn/Text {:style (.-linkText styles)} (strings/texts :forgotpass)]]
      [:> rn/Pressable [:> rn/Text {:style (.-linkText styles)} (strings/texts :lockedout)]]
      [:> rn/Pressable [:> rn/Text {:style (.-linkText styles)} (strings/texts :contact)]]]]))
