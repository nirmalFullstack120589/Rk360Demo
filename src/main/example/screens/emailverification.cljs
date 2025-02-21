(ns example.screens.emailverification
  (:require
   ["react-native" :as rn]
   [example.widgets :refer [input-field]]
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
   :title {:font-size 24 :color "#000000" :margin-left 10}
   :back-button {:width 30 :height 25 :margin-bottom 10 :margin-right 15 :margin-left 10}
   :content {:flex 1 :margin 30}
   :text-title {:font-size 24 :font-weight 600 :margin-bottom 10}
   :text-body {:font-size 12 :margin-bottom 10}
   :button {:background-color "#87CEEB"
            :width 130
            :height 50
            :justify-content :center
            :align-items :center
            :margin-left 140
            :border-radius 10}
   :button-text {:color "#ffffff"}})

(defn emailverify [^js props]
  (let [email-param (r/atom "test@gmail.com")
        email (r/atom (or email-param ""))]

    [:> rn/SafeAreaView {:style (:container styles)}
     [:> rn/View {:style (:header styles)}
      [:> rn/View {:style (:header-content styles)}
       [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "Information"))}
        [:> rn/Image {:source (js/require "../assets/back.png")
                      :style (:back-button styles)}]]
       [:> rn/Image {:source (js/require "../assets/rk-logo24.png")
                     :style (:logo styles)}]
       [:> rn/Text {:style (:title styles)} (strings/texts :app-title)]]]
     [:> rn/View {:style (:content styles)}

      [:> rn/Text {:style (:text-title styles)} (strings/texts :verifyemail)]
      [:> rn/Text {:style (:text-body styles)}
       (strings/texts :verificationemail)]
      [:> rn/Text {:style (:text-body styles)}
       (strings/texts :resendemailtext)]

      [:> rn/View
       [input-field {:label "Email"
                     :placeholder "Enter your email"
                     :value "test@gmail.com"
                     :on-change #(reset! email %)}]
       [:> rn/Pressable {:on-press #(-> props .-navigation (.navigate "EmailSuccess"))
                         :style (:button styles)}
        [:> rn/Text {:style (:button-text styles)} (strings/texts :confirmemailbutton)]]]]]))
