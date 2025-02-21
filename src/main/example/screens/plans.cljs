(ns example.screens.plans
   (:require
    ["react-native" :as rn]
    [reagent.core :as r]
    [example.utils.APIManager :as apiConstants]
    [example.components.planscomponent :as render-item]
    [example.utils.strings :as strings]))

 (def styles
   (-> (clj->js
        {:container {:flex 1 :backgroundColor "white"}
         :headerContainer {:height 70
                           :width "100%"
                           :justifyContent "flex-start"
                           :alignItems "center"
                           :backgroundColor "white"
                           :borderBottomWidth 1
                           :borderColor "#e8e8e8"
                           :marginTop 25}
         :headerRow {:flexDirection "row"
                     :alignItems "center"
                     :marginTop 10
                     :justifyContent "flex-start"
                     :width "100%"}
         :backButton {:width 30 :height 25 :marginBottom 10 :marginRight 15 :marginLeft 10}
         :logo {:width 50 :height 50 :marginBottom 10}
         :title {:fontSize 24 :color "#000000" :marginLeft 10}
         :scrollContainer {:backgroundColor "#fff"
                           :marginRight 15}
         :introText {:fontSize 11
                     :color "#000"
                     :margin 20
                     :marginBottom 20
                     :textAlign "left"}
         :radioContainer {:flexDirection "row"
                          :alignItems "center"
                          :justifyContent "center"
                          :margin 10
                          :marginLeft 25
                          :marginBottom 20}
         :radioOption {:flexDirection "row"
                       :alignItems "center"
                       :marginRight 20}
         :radioButton {:width 24
                       :height 24
                       :borderColor "#4682B4"
                       :alignItems "center"
                       :justifyContent "center"
                       :borderRadius 20
                       :borderWidth 1.5}
         :radioSelected {:width 16
                         :height 16
                         :backgroundColor "#87CEEB"
                         :borderRadius 20}
         :radioText {:fontSize 16 :color "#000" :marginLeft 5}
         :couponContainer {:backgroundColor "#fff"
                           :padding 10
                           :borderRadius 5
                           :marginBottom 20}
         :termsContainer {:flexDirection "row"
                          :justifyContent "center"
                          :width "96%"
                          :margin 10}
         :checkbox {:width 30 :height 30}
         :checkboxChecked {:width 24 :height 24}
         :checkboxUnchecked {:width 20
                             :height 20
                             :borderColor "#70CCF8"
                             :borderRadius 6
                             :borderWidth 1}
         :termsText {:fontSize 12
                     :color "#b5b5b5"
                     :marginLeft 5
                     :marginBottom 15
                     :width "90%"}
         :couponInput {:borderWidth 1
                       :borderColor "#ccc"
                       :padding 10
                       :fontSize 16
                       :borderRadius 10
                       :flex 1
                       :marginRight 10}
         :couponButton {:height 40
                        :paddingHorizontal 15
                        :justifyContent "center"
                        :alignItems "center"
                        :borderRadius 5}
         :couponButtonEnabled {:backgroundColor "#87CEEB"}
         :couponButtonDisabled {:backgroundColor "#cccccc"}
         :couponButtonText {:color "white" :fontSize 16}
         :discountInfo {:fontSize 14 :color "#000" :margin 30 :marginTop 10}})
       (rn/StyleSheet.create)))

 (def plan-data (js->clj (js/require "../assets/plans.json") :keywordize-keys true))

 (def selected-plan-monthly (r/atom true))
 (def selected-plan-yearly (r/atom false))
 (def user-input (r/atom ""))
 (def agreed? (r/atom true))

 (defn apply-coupon [navigation]
   (if (empty? @user-input)
     (js/alert (strings/texts :entercoupontext))
     (do
       (println "Calling API with coupon code:" @user-input)
       (-> (apiConstants/api-request "POST" :validate-coupon {:coupon @user-input})
           (.then (fn [response]
                    (js/console.log "API Response:" response)
                    (-> navigation (.navigate "plansDetails"))))
           (.catch (fn [error]
                     (js/console.error "Error occurred:" error)))))))

(defn plans [^js props]
  (let [navigation (.-navigation props)]
    [:> rn/SafeAreaView {:style (.-container styles)}

     [:> rn/View {:style (.-headerContainer styles)}
      [:> rn/View {:style (.-headerRow styles)}
       [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "SignIn"))}
        [:> rn/Image {:source (js/require "../assets/back.png")
                      :style (.-backButton styles)}]]
       [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                     :style (.-logo styles)}]
       [:> rn/Text {:style (.-title styles)} (strings/texts :app-title)]]]

     [:> rn/ScrollView {:style (.-scrollContainer styles)}

      [:> rn/Text {:style (.-introText styles)}
       (strings/texts :planstext)]

      [:> rn/View {:style (.-radioContainer styles)}
       [:> rn/View {:style (.-radioOption styles)}
        [:> rn/Pressable
         {:onPress #(do (reset! selected-plan-monthly true) (reset! selected-plan-yearly false))
          :style (.-radioButton styles)}
         (when @selected-plan-monthly
           [:> rn/View {:style (.-radioSelected styles)}])]
        [:> rn/Text {:style (.-radioText styles)} (strings/texts :Monthly)]]

       [:> rn/View {:style (.-radioOption styles)}
        [:> rn/Pressable
         {:onPress #(do (reset! selected-plan-monthly false) (reset! selected-plan-yearly true))
          :style (.-radioButton styles)}
         (when @selected-plan-yearly
           [:> rn/View {:style (.-radioSelected styles)}])]
        [:> rn/Text {:style (.-radioText styles)} (strings/texts :Yearly)]]]

      (for [plan plan-data]
        ^{:key (str (:title plan) "-" (:header plan))}
        [render-item/render-item {:item plan :selected-plan-monthly selected-plan-monthly}])

      [:> rn/View {:style (.-couponContainer styles)}
       [:> rn/View {:style (.-termsContainer styles)}
        [:> rn/Pressable {:onPress #(reset! agreed? (not @agreed?))
                          :style (.-checkbox styles)}
         (if @agreed?
           [:> rn/Image {:source (js/require "../assets/checked-icon.png")
                         :style (.-checkboxChecked styles)}]
           [:> rn/View {:style (.-checkboxUnchecked styles)}])]
        [:> rn/Text {:style (.-termsText styles)}
         (strings/texts :planagree)]]

       [:> rn/View {:style {:flexDirection "row"
                            :alignItems "center"
                            :justifyContent "space-between"
                            :margin 10}}

        [:> rn/TextInput {:style (.-couponInput styles)
                          :placeholder "Coupon Code"
                          :value @user-input
                          :onChangeText #(reset! user-input %)}]

        [:> rn/TouchableOpacity {:style [(.-couponButton styles)
                                         (if @agreed?
                                           (.-couponButtonEnabled styles)
                                           (.-couponButtonDisabled styles))]
                                 :onPress (when @agreed? #(apply-coupon navigation))
                                 :disabled (not @agreed?)}
         [:> rn/Text {:style (.-couponButtonText styles)} (strings/texts :DoneButton)]]]]

      [:> rn/Text {:style (.-discountInfo styles)}
       (strings/texts :discountInfo)]]]))
