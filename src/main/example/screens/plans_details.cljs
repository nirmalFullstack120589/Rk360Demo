(ns example.screens.plans-details
  (:require
   ["react-native" :as rn]
   [example.components.plansDetailcomponent :refer [render-item]]
   [reagent.core :as r]
   [example.utils.strings :as strings]))

(def styles
  {:container {:flex 1 :background-color "white"}
   :header {:height 70
            :width "100%"
            :justify-content :flex-start
            :align-items :center
            :background-color "white"
            :border-bottom-width 1
            :border-color "#e8e8e8"
            :margin-top 25}
   :header-content {:flex-direction "row"
                    :align-items :center
                    :margin-top 10
                    :justify-content :flex-start
                    :width "100%"}
   :logo {:width 50 :height 50 :margin-bottom 10}
   :back-button {:width 30 :height 25 :margin-bottom 10 :margin-right 15 :margin-left 10}
   :title {:font-size 24 :color "#000000" :margin-left 10}
   :scroll {:background-color "#fff" :margin-right 5}
   :promo-container {:margin 10 :padding 10 :flex-direction "row" :width "94%"}
   :promo-text {:font-size 18 :font-weight "bold" :color "#000"}
   :promo-superscript {:font-size 10 :color "#000"}
   :description {:font-size 11 :color "#000" :margin 20 :margin-bottom 10 :text-align :flex-start}
   :coupon-button {:margin 10 :padding-bottom 10 :justify-content :center :align-items :center}
   :coupon-text {:font-size 16 :color "#87CEEB"}
   :plan-container {:background-color "#fff" :padding 10 :border-radius 5 :margin-bottom 20}
   :agreement-container {:flex-direction "row" :justify-content "flex-start" :width "96%" :margin 10}
   :checkbox {:width 30 :height 30}
   :checked-icon {:width 24 :height 24}
   :unchecked-box {:width 20 :height 20 :border-color "#70CCF8" :border-radius 6 :border-width 1}
   :agreement-text {:font-size 12 :color "#b5b5b5" :margin-left 5 :margin-bottom 15 :width "90%"}})

(def plan-data (js->clj (js/require "../assets/plans_free.json") :keywordize-keys true))
(def agreed? (r/atom false))

(defn plans-details [^js props]
  (let [navigation (.-navigation props)]
    [:> rn/SafeAreaView {:style (:container styles)}
     [:> rn/View {:style (:header styles)}
      [:> rn/View {:style (:header-content styles)}
       [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "Plans"))}
        [:> rn/Image {:source (js/require "../assets/back.png") :style (:back-button styles)}]]
       [:> rn/Image {:source (js/require "../assets/rk-logo24.png") :style (:logo styles)}]
       [:> rn/Text {:style (:title styles)} (strings/texts :app-title)]]]
     [:> rn/ScrollView {:style (:scroll styles)}
      [:> rn/Pressable {:style (:promo-container styles)}
       [:> rn/Text {:style (:promo-text styles)} (strings/texts :confusedrk360)
        [:> rn/Text {:style (:promo-superscript styles)} (strings/texts :reserveright)]
        [:> rn/Text {:style (:promo-text styles)} (strings/texts :naviapp)]]]
      [:> rn/Text {:style (:description styles)}
       (strings/texts :planstext)]
      [:> rn/Pressable {:style (:coupon-button styles) :on-press #(-> props .-navigation (.goBack))}
       [:> rn/Text {:style (:coupon-text styles)} (strings/texts :removecoupon)]]
      (for [plan plan-data]
        ^{:key (str (:title plan) "-" (:header plan))}
        [render-item {:item plan :navigation navigation :agreed? agreed?}])
      [:> rn/View {:style (:plan-container styles)}
       [:> rn/View {:style (:agreement-container styles)}
        [:> rn/Pressable {:onPress #(reset! agreed? (not @agreed?)) :style (:checkbox styles)}
         (if @agreed?
           [:> rn/Image {:source (js/require "../assets/checked-icon.png") :style (:checked-icon styles)}]
           [:> rn/View {:style (:unchecked-box styles)}])]
        [:> rn/Text {:style (:agreement-text styles)}
         (strings/texts :policytext)]]]]]))
