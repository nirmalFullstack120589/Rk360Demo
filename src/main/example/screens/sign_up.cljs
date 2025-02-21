(ns example.screens.sign-up
  (:require
   ["react-native" :as rn]
   [example.service.auth :refer [check-contact validate-user]]
   [example.widgets :refer [input-field password-field]]
   [reagent.core :as r]
   [example.utils.strings :as strings]))

(def styles
  (.create rn/StyleSheet
           (clj->js {:container {:flex 1 :justifyContent :center :backgroundColor "white"}
                     :headerContainer {:height 70
                                       :width "100%"
                                       :justifyContent :flex-start
                                       :alignItems :center
                                       :backgroundColor "white"
                                       :borderBottomWidth 1
                                       :borderColor "#e8e8e8"
                                       :marginTop 25}
                     :headerContent {:flexDirection "row"
                                     :alignItems "center"
                                     :marginTop 10
                                     :justifyContent "flex-start"
                                     :width "100%"}
                     :backButton {:width 30
                                  :height 25
                                  :marginBottom 10
                                  :marginRight 15
                                  :marginLeft 10}
                     :logoImage {:width 50
                                 :height 50
                                 :marginBottom 10}
                     :titleText {:fontSize 24 :color "#000000" :marginLeft 10}
                     :sectionTitle {:fontSize 24
                                    :fontWeight 700
                                    :color "#4682B4"}
                     :sectionDescription {:fontSize 11
                                          :color "#000000"}
                     :linkText {:fontSize 14 :color "#87CEEB"}
                     :inputFieldContainer {:margin 15}
                     :buttonContainer {:justifyContent :center
                                       :alignItems :center}
                     :primaryButton {:justifyContent :center
                                     :alignItems :center
                                     :borderRadius 5
                                     :borderWidth 0
                                     :marginTop 5
                                     :width 150
                                     :marginBottom 10
                                     :backgroundColor "#87CEEB"
                                     :height 50}
                     :primaryButtonText {:color "#ffffff"}
                     :secondaryButton {:justifyContent :center
                                       :alignItems :center
                                       :backgroundColor "#fff"
                                       :borderRadius 5
                                       :borderWidth 0.5
                                       :marginTop 10
                                       :marginBottom 30
                                       :width 150
                                       :height 50
                                       :borderColor "#87CEEB"}
                     :secondaryButtonText {:color "#4682B4"}})))

(defn sign-up [^js props]
  (r/with-let [first-name (r/atom "")
               last-name (r/atom "")
               email (r/atom "")
               mobile-phone (r/atom "")
               backup-mobile-phone (r/atom "")
               password (r/atom "")
               password-confirmation (r/atom "")
               verbal-password (r/atom "")
               confirm-verbal-password (r/atom "")
               show-password? (r/atom false)
               show-password-confirm? (r/atom false)
               show-verbal-password? (r/atom false)
               show-verbal-confirm-password? (r/atom false)
               error-first-name (r/atom false)
               error-last-name (r/atom false)
               error-email (r/atom false)
               error-mobile-phone (r/atom false)
               error-backup-mobile-phone (r/atom false)
               error-password (r/atom false)
               error-password-confirmation (r/atom false)
               error-verbal-password (r/atom false)
               error-confirm-verbal-password (r/atom false)
               validation-result (r/atom false)
               loading? (r/atom false)]

    (defn validate-fields [on-success]
      (reset! error-email (empty? @email))
      (reset! error-mobile-phone (empty? @mobile-phone))

      (if (or (empty? @email) (empty? @mobile-phone))
        (do
          (reset! validation-result false)
          (js/alert (strings/texts :signupemailphone)))
        (do
          (reset! loading? true)
          (-> (validate-user @email @mobile-phone)
              (.then (fn [response]
                       (reset! loading? false)
                       (if (= "ok" (:status response))
                         (do
                           (reset! validation-result true)
                           (println "Validation successful!")
                           (on-success))
                         (do
                           (reset! validation-result false)
                           (println "Validation failed:" response)))))))))

    (defn handle-sign-up []
      (validate-fields
       (fn []
         (js/console.log "Inside on-success callback! Fetching user ID...")

         (-> (check-contact "email" @email)
             (.then (fn [response]
                      (let [user-id (:id response)]
                        (if (some? user-id)
                          (do
                            (js/console.log "User ID found:" user-id)
                            (let [params {:user-id user-id
                                          :first-name @first-name
                                          :last-name @last-name
                                          :email @email
                                          :mobile-phone @mobile-phone
                                          :backup-mobile-phone @backup-mobile-phone
                                          :password @password
                                          :password-confirmation @password-confirmation
                                          :verbal-password @verbal-password
                                          :confirm-verbal-password @confirm-verbal-password}]
                              (js/console.log "Navigating to OTP screen with params:" (clj->js params))
                              (-> props .-navigation (.navigate "OTP" params {:email @email}))))
                          (do
                            (js/console.warn "No user ID returned from check-contact")
                            (js/alert (strings/texts :retrievaltext)))))))
             (.catch (fn [error]
                       (js/console.error "Error while checking contact:" error)
                       (js/alert (strings/texts :errorverifycode))))))))


    [:> rn/SafeAreaView {:style (.-container styles)}
     [:> rn/View {:style (.-headerContainer styles)}
      [:> rn/View {:style (.-headerContent styles)}
       [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "plansDetails"))}
        [:> rn/Image {:source (js/require "../assets/back.png")
                      :style (.-backButton styles)}]]
       [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                     :style (.-logoImage styles)}]
       [:> rn/Text {:style (.-titleText styles)} (strings/texts :app-title)]]]
     [:> rn/ScrollView {:style {:flex 1 :paddingLeft 16 :paddingRight 16 :marginTop 10}}
      [:> rn/View {:style {:width "100%" :height 30 :marginTop 10 :alignSelf "center"
                           :justifyContent "center" :alignItems "center" :flexDirection "row"}}
       [:> rn/Text {:style (.-sectionTitle styles)} (strings/texts :signupheader)]]

      [:> rn/View {:style {:margin 15}}
       [:> rn/Text {:style (.-sectionDescription styles)} (strings/texts :descriptionsignup)]
       [:> rn/Pressable {:style {:marginTop 5} :on-press #(-> props .-navigation (.navigate "Plans"))}
        [:> rn/Text {:style (.-linkText styles)} (strings/texts :planselection)]]]

      [:> rn/View {:style (.-inputFieldContainer styles)}
       [input-field {:placeholder "First Name"
                     :label "First Name"
                     :value @first-name
                     :on-change #(reset! first-name %)
                     :error @error-first-name}]

       [input-field {:placeholder "Last Name"
                     :label "Last Name"
                     :value @last-name
                     :on-change #(reset! last-name %)
                     :error @error-last-name}]

       [input-field {:label "E-mail*"
                     :placeholder "E-mail"
                     :value @email
                     :on-change #(reset! email %)
                     :error @error-email}]

       [input-field {:label "Mobile Phone*"
                     :placeholder "Mobile Phone"
                     :value @mobile-phone
                     :on-change #(reset! mobile-phone %)
                     :error @error-mobile-phone}]

       [input-field {:placeholder "Backup Mobile Phone"
                     :label "Backup Mobile Phone"
                     :value @backup-mobile-phone
                     :on-change #(reset! backup-mobile-phone %)
                     :error @error-backup-mobile-phone}]

       [password-field {:placeholder "Password"
                        :label "Password"
                        :value @password
                        :on-change #(reset! password %)
                        :show-password? @show-password?
                        :toggle-password #(swap! show-password? not)
                        :error @error-password}]

       [password-field {:placeholder "Password Confirmation"
                        :label "Password Confirmation"
                        :value @password-confirmation
                        :on-change #(reset! password-confirmation %)
                        :show-password? @show-password-confirm?
                        :toggle-password #(swap! show-password-confirm? not)
                        :error @error-password-confirmation}]

       [password-field {:placeholder "Verbal Password"
                        :label "Verbal Password (It should be at least two words and 8 characters long)"
                        :value @verbal-password
                        :on-change #(reset! verbal-password %)
                        :show-password? @show-verbal-password?
                        :toggle-password #(swap! show-verbal-password? not)
                        :error @error-verbal-password}]

       [password-field {:placeholder "Confirm Verbal Password"
                        :label "Confirm Verbal Password"
                        :value @confirm-verbal-password
                        :on-change #(reset! confirm-verbal-password %)
                        :show-password? @show-verbal-confirm-password?
                        :toggle-password #(swap! show-verbal-confirm-password? not)
                        :error @error-confirm-verbal-password}]]

      [:> rn/View {:style (.-buttonContainer styles)}
       [:> rn/Pressable {:style (.-primaryButton styles)
                         :disabled (or @loading? (when-not @validation-result))
                         :on-press handle-sign-up}
        [:> rn/Text {:style (.-primaryButtonText styles)} (strings/texts :signupheader)]]

       [:> rn/Pressable {:style (.-secondaryButton styles)
                         :on-press #(-> props .-navigation (.navigate "Plans"))}
        [:> rn/Text {:style (.-secondaryButtonText styles)} (strings/texts :backbutton)]]]]]))
