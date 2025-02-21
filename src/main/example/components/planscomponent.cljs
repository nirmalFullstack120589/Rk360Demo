(ns example.components.planscomponent
  (:require
   ["react-native" :as rn]))

(def styles
  (-> (clj->js
       {:container {:padding 16
                    :backgroundColor "rgb(238, 238, 238)"
                    :marginLeft 20
                    :marginRight 20
                    :marginBottom 20
                    :borderRadius 10
                    :width "92%"}
        :headerText {:fontSize 12
                     :color "#666"
                     :marginTop 5}
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
        :descContainer {:marginTop 10}
        :descItem {:flexDirection "row"
                   :alignItems "center"
                   :marginTop 5}
        :descBullet {:fontSize 14
                     :color "#333"
                     :marginRight 10}
        :descText {:fontSize 12
                   :color "#333"}
        :benefitsContainer {:marginTop 10
                            :width "90%"}
        :benefitItem {:flexDirection "row"
                      :alignItems "center"
                      :marginTop 5}
        :benefitStar {:fontSize 14
                      :color "#FFD700"
                      :marginRight 10}})
      (rn/StyleSheet.create)))

(defn render-item [{:keys [item selected-plan-monthly]}]
  (let [data (js->clj item :keywordize-keys true)
        price (if @selected-plan-monthly (:price_monthly data) (:price_yearly data))]

    [:> rn/View {:style (.-container styles)}
     [:> rn/Text {:style (.-headerText styles)} (:header data)]
     [:> rn/Text {:style (.-titleText styles)} (:title data)]
     (when (and price (not= price ""))
       [:> rn/Text {:style (.-priceText styles)} (str "" price)])

     [:> rn/TouchableOpacity
      {:style (.-button styles)
       :opacity (if :disabled? 0.5 1)
       :disabled true}
      [:> rn/Text {:style (.-buttonText styles)} (:free_plan data)]]

     [:> rn/View {:style (.-descContainer styles)}
      (for [desc (:description data)]
        [:> rn/View {:key desc :style (.-descItem styles)}
         [:> rn/Text {:style (.-descBullet styles)} "•"]
         [:> rn/Text {:style (.-descText styles)} desc]])]

     (when (seq (:benefits data))
       [:> rn/View {:style (.-benefitsContainer styles)}
        (for [benefit (:benefits data)]
          [:> rn/View {:key benefit :style (.-benefitItem styles)}
           [:> rn/Text {:style (.-benefitStar styles)} "★"]
           [:> rn/Text {:style (.-descText styles)} benefit]])])]))
