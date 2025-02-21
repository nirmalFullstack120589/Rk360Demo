(ns example.screens.information-details
  (:require
   ["react-native" :as rn]
   [example.utils.strings :as strings]
   [reagent.core :as r]))

(def styles
  {:container {:flex 1 :background-color "white"}
   :header {:height 70 :width "100%" :justify-content :flex-start :align-items :center :background-color "white" :border-bottom-width 1 :border-color "#e8e8e8" :margin-top 25}
   :header-content {:flex-direction "row" :align-items :center :margin-top 10 :justify-content :flex-start :width "100%"}
   :back-button {:width 30 :height 25 :margin-bottom 10 :margin-right 15 :margin-left 10}
   :logo {:width 50 :height 50 :margin-bottom 10}
   :title {:font-size 24 :color "#000000" :margin-left 10}
   :scroll-view {:flex 1 :margin-top 10 :margin-right 10 :margin-left 10 :padding 15}
   :scroll-content {:align-items :center :justify-content :center}
   :section-title {:font-size 20 :font-weight "600" :margin-bottom 20}
   :paragraph {:font-size 10 :margin-bottom 20 :text-align "flex-start"}
   :input-label {:font-size 12 :font-weight "600" :margin-bottom 5}
   :text-input {:font-size 12 :color "#888" :margin-bottom 20}
   :info-text {:font-size 12.5 :margin-bottom 20 :text-align "flex-start"}
   :checkbox-container {:background-color "#e8e8e8" :width 320 :padding 15 :border-radius 10 :margin-bottom 20 :margin-top 15}
   :checkbox-row {:flex-direction "row" :align-items "flex-start" :justify-content "space-between"}
   :checkbox {:width 24 :height 24  :border-color "#70CCF8" :border-radius 6 :border-width 2 :margin 10}
   :checkbox-text {:font-size 12 :margin-bottom 10}
   :points {:margin-left 10}
   :point-text {:font-size 10}
   :agree-button-enabled {:height 40 :background-color "#87CEEB" :padding-horizontal 10 :justify-content "center" :align-items "center" :border-radius 5 :margin-top 20 :margin-bottom 50}
   :agree-button-disabled {:height 40 :background-color "#cccccc" :padding-horizontal 10 :justify-content "center" :align-items "center" :border-radius 5 :margin-top 20 :margin-bottom 50}
   :agree-button-text {:color "white" :font-size 16}})

(let [agreed-first? (r/atom false)
      agreed-second? (r/atom false)]
  (defn information [^js props]
    [:> rn/SafeAreaView {:style (:container styles)}
     [:> rn/View {:style (:header styles)}
      [:> rn/View {:style (:header-content styles)}
       [:> rn/Pressable {:onPress #(-> props .-navigation (.navigate "OTP"))}
        [:> rn/Image {:source (js/require "../assets/back.png") :style (:back-button styles)}]]
       [:> rn/Image {:source (js/require "../assets/rk-logo24.png") :style (:logo styles)}]
       [:> rn/Text {:style (:title styles)} (strings/texts :app-title)]]]
     [:> rn/ScrollView {:style (:scroll-view styles) :contentContainerStyle (:scroll-content styles)}
      [:> rn/Text {:style (:section-title styles)} (strings/texts :section-title)]
      [:> rn/Text {:style (:paragraph styles)} (strings/texts :paragraph)]
      [:> rn/Text {:style (:input-label styles)} (strings/texts :username-label)]
      [:> rn/TextInput {:editable false :style (:text-input styles) :value "test@gmail.com"}]
      [:> rn/Text {:style (:info-text styles)} (strings/texts :info-text-username)]
      [:> rn/Text {:style (:input-label styles)} (strings/texts :recovery-label)]
      [:> rn/Text {:style (:text-input styles)} (strings/texts :recovery-phrase)]
      [:> rn/Text {:style (:info-text styles)} (strings/texts :info-text-recovery)]
      [:> rn/Pressable {:style (:checkbox-container styles) :onPress #(reset! agreed-first? (not @agreed-first?))}
       [:> rn/View {:style (:checkbox-row styles)}
        [:> rn/View {:style (:checkbox styles)} (if @agreed-first? [:> rn/Image {:source (js/require "../assets/checked-icon.png") :style {:width 23 :height 23 :margin-bottom 2 :margin-right 2}}] nil)]
        [:> rn/View {:style {:flex 1}}
         [:> rn/Text {:style (:checkbox-text styles)} (strings/texts :checkbox-text-1)]
         [:> rn/View {:style (:points styles)}
          [:> rn/Text {:style (:point-text styles)} (strings/texts :username)]
          [:> rn/Text {:style (:point-text styles)} (strings/texts :recoverytext)]
          [:> rn/Text {:style (:point-text styles)} (strings/texts :verbal-password)]
          [:> rn/Text {:style (:point-text styles)} (strings/texts :emailaddress)]
          [:> rn/Text {:style (:point-text styles)} (strings/texts :mobilenumber)]]]]]
      [:> rn/Pressable {:style (:checkbox-container styles) :onPress #(reset! agreed-second? (not @agreed-second?))}
       [:> rn/View {:style (:checkbox-row styles)}
        [:> rn/View {:style (:checkbox styles)} (if @agreed-second? [:> rn/Image {:source (js/require "../assets/checked-icon.png") :style {:width 23 :height 23 :margin-bottom 2 :margin-right 2}}] nil)]
        [:> rn/View {:style {:flex 1}}
         [:> rn/Text {:style (:checkbox-text styles)} (strings/texts :checkbox-text-2 )]]]]
      [:> rn/TouchableOpacity {:style (if (and @agreed-first? @agreed-second?) (:agree-button-enabled styles) (:agree-button-disabled styles))
                               :onPress (when (and @agreed-first? @agreed-second?) #(-> props .-navigation (.navigate "EmailVerify")))
                               :disabled (not (and @agreed-first? @agreed-second?))}
       [:> rn/Text {:style (:agree-button-text styles)} (strings/texts :agreebuttontext)]]]]))