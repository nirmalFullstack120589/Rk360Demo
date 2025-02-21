(ns example.components.plansDetailcomponent
  (:require
   ["react-native" :as rn]
   [example.utils.strings :as strings]))

(def styles
  (-> (clj->js
       {:container {:padding 16
                    :backgroundColor "rgb(238, 238, 238)"
                    :marginLeft 20
                    :marginRight 20
                    :marginBottom 20
                    :borderRadius 10
                    :width "90%"}
        :titleText {:fontSize 14
                    :fontWeight "bold"
                    :color "#000"
                    :marginTop 10}
        :priceText {:fontSize 14
                    :color "#000"
                    :marginTop 10}
        :button {:flexDirection "row"
                 :alignItems "center"
                 :backgroundColor "#87CEEB"
                 :padding 10
                 :borderRadius 5
                 :justifyContent "space-between"
                 :marginTop 10}
        :buttonText {:color "white"
                     :fontWeight "bold"
                     :fontSize 16}
        :icon {:width 20
               :height 20}
        :descContainer {:marginTop 10}
        :descItem {:flexDirection "row"
                   :alignItems "center"
                   :marginTop 5}
        :descBullet {:fontSize 14
                     :color "#333"
                     :marginRight 10}
        :descText {:fontSize 12
                   :color "#333"}})
      (rn/StyleSheet.create)))

(defn render-item [{:keys [item navigation agreed?]}]
  (let [data (js->clj item :keywordize-keys true)]
    [:> rn/View {:style (.-container styles)}

     [:> rn/Text {:style (.-titleText styles)} (:title data)]
     [:> rn/Text {:style (.-priceText styles)} (str "" (:price data))]

     [:> rn/TouchableOpacity
      {:style (.-button styles)
       :onPress (fn []
                  (if @agreed?
                    (-> navigation (.navigate "SignUp" {:agreed @agreed?}))
                    (js/alert (strings/texts :checkboxagree))))}
      :disabled (not @agreed?)
      [:> rn/Text {:style (.-buttonText styles)} (:free_plan data)]
      [:> rn/Image {:source (js/require "../assets/next-arrow.png")
                    :style (.-icon styles)}]]

     [:> rn/View {:style (.-descContainer styles)}
      (for [desc (:description data)]
        [:> rn/View {:key desc :style (.-descItem styles)}
         [:> rn/Text {:style (.-descBullet styles)} "•"]
         [:> rn/Text {:style (.-descText styles)} desc]])]]))
