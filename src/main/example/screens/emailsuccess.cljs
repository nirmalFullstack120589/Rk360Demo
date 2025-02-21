(ns example.screens.emailsuccess
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
            :border-bottom-width 0.5
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
   :content {:flex 1 :margin 30}
   :heading {:font-size 20 :font-weight "600" :margin-bottom 20}
   :text {:font-size 12 :margin-bottom 20}
   :button {:border-radius 10
            :background-color "#87CEEB"
            :width 100
            :height 40
            :justify-content "center"
            :align-items "center"
            :margin-top 10}
   :button-text {:color "#fff"}})

(defn email-success [^js props]
  [:> rn/SafeAreaView {:style (:container styles)}
   [:> rn/View {:style (:header styles)}
    [:> rn/View {:style (:header-content styles)}
     [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "EmailVerify"))}
      [:> rn/Image {:source (js/require "../assets/back.png")
                    :style (:back-button styles)}]]
     [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                   :style (:logo styles)}]
     [:> rn/Text {:style (:title styles)} (strings/texts :app-title)]]]

   [:> rn/View {:style (:content styles)}
    [:> rn/Text {:style (:heading styles)} (strings/texts :verificationtext)]
    [:> rn/Text {:style (:text styles)}
     (strings/texts :credentialstext)]
    [:> rn/Pressable {:style (:button styles) :onPress #(-> props .-navigation (.navigate "Success"))}
     [:> rn/Text {:style (:button-text styles)} (strings/texts :Nextbutton)]]]])