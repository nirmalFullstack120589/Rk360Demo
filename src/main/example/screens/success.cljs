(ns example.screens.success
  (:require
   ["react-native" :as rn]
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
   :content {:flex 1 :margin 30 :padding-top 20 :align-items :center}
   :heading {:font-size 20 :font-weight "600" :margin-bottom 10}
   :text {:font-size 16 :margin-bottom 10}})

(defn success [^js props]
  [:> rn/SafeAreaView {:style (:container styles)}
   [:> rn/View {:style (:header styles)}
    [:> rn/View {:style (:header-content styles)}
     [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "EmailSuccess"))}
      [:> rn/Image {:source (js/require "../assets/back.png")
                    :style (:back-button styles)}]]
     [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                   :style (:logo styles)}]
     [:> rn/Text {:style (:title styles)} (strings/texts :app-title)]]]
   [:> rn/View {:style (:content styles)}
    [:> rn/Text {:style (:heading styles)} (strings/texts :paymentprocess)]
    [:> rn/Text {:style (:text styles)} (strings/texts :subscriptionsapprove)]]])