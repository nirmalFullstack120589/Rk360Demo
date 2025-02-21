(ns example.widgets
  (:require ["react-native" :as rn]
            [reagent.core :as r]))


(def eye-icon (js/require "../assets/view.png"))
(def hide-icon (js/require "../assets/hide.png"))
(defn button [{:keys [style text-style on-press
                      disabled? disabled-style 
                      variant background-color border-color text-color]
               :or {on-press #()
                    variant :filled
                    background-color "#4472C4"
                    border-color "#4472C4"
                    text-color "white"}} text]
  (let [base-style {:width          "100%"
                    :height         50
                    :border-bottom-width 1
                    :justify-content :center
                    :align-items    :center}
        filled-style {:background-color background-color}
        outlined-style {:background-color "white"
                        :border-width 1
                        :border-color border-color}
        final-style (merge base-style
                           (if (= variant :outlined) outlined-style filled-style)
                           style)]
    [:> rn/Pressable {:style (cond-> final-style
                               disabled? (merge {:background-color "#aaaaaa"}
                                                disabled-style))
                      :on-press on-press
                      :disabled disabled?}
     [:> rn/Text {:style (merge {:font-size 16
                                 :color (if disabled? "#ffffff" text-color)}
                                text-style)}
      text]]))

(defn input-field [{:keys [label placeholder value on-change style secure-text-entry keyboard-type error]}]
  (let [border-color (if error "red" "#cccccc")]
    [:> rn/View {:style {:margin-bottom 20}}
     (when label
       [:> rn/Text {:style {:font-size 14 :margin-bottom 5 :font-weight 600}} label])

     [:> rn/TextInput
      {:style (merge {:border-width 1
                      :border-color border-color
                      :padding 16
                      :border-radius 5
                      :font-size 16}
                     style)
       :placeholder placeholder
       :secure-text-entry (boolean secure-text-entry)
       :value value
       :on-change-text on-change
       :keyboard-type keyboard-type}]]))


(defn password-field [{:keys [label placeholder value on-change style show-password? toggle-password]}]
  [:> rn/View {:style {:margin-bottom 20}}
   (when label
     [:> rn/Text {:style {:font-size 14 :margin-bottom 5 :font-weight 600}} label])

   [:> rn/View {:style {:flex-direction "row"
                        :align-items "center"
                        :border-width 1
                        :padding-left 10
                        :padding-right 10
                        :border-radius 5
                        :height 55
                        :border-color "#cccccc"}} 
    
    [:> rn/TextInput {:style (merge {:flex 1
                                     :font-size 16}
                                    style)
                      :secure-text-entry (not show-password?)
                      :placeholder placeholder
                      :value value
                      :on-change-text on-change}]
    
    [:> rn/TouchableOpacity {:on-press toggle-password}
     [:> rn/Image {:source (if show-password? hide-icon eye-icon)
                   :style  {:width 30 :height 30 :margin-left 10}}]]]])
