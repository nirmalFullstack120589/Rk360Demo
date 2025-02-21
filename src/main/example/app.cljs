(ns example.app
  (:require [example.events]
            [example.subs]
            [expo.root :as expo-root]
            [re-frame.core :as rf]
            [reagent.core :as r]
            ["react-native" :as rn]
            ["@react-navigation/native" :as rnn]
            ["@react-navigation/native-stack" :as rnn-stack]
            [example.screens.sign-in :refer [sign-in]]
            [example.screens.sign-up :refer [sign-up]]
            [example.screens.otp :refer [otp-screen]]
            [example.screens.plans :refer [plans]]
            [example.screens.plans-details :refer [plans-details]]
            [example.screens.success :refer [success]]
            [example.screens.information-details :refer [information]]
            [example.screens.emailverification :refer [emailverify]]
            [example.screens.emailsuccess :refer [email-success]]))

(defonce Stack (rnn-stack/createNativeStackNavigator))

(set! (.-log js/console) (fn [& _] nil))
(set! (.-warn js/console) (fn [& _] nil))
(set! (.-error js/console) (fn [& _] nil))

(defn root []
  [:> rnn/NavigationContainer
   [:> Stack.Navigator  {:screen-options {:headerShown false}}
    [:> Stack.Screen {:name "SignIn" :component (fn [props] (r/as-element [sign-in props]))}]
    [:> Stack.Screen {:name "SignUp" :component (fn [props] (r/as-element [sign-up props]))}]
    [:> Stack.Screen {:name "OTP" :component (fn [props] (r/as-element [otp-screen props]))}]
    [:> Stack.Screen {:name "Plans" :component (fn [props] (r/as-element [plans props]))}]
    [:> Stack.Screen {:name "plansDetails" :component (fn [props] (r/as-element [plans-details props]))}]
    [:> Stack.Screen {:name "Success" :component (fn [props] (r/as-element [success props]))}]
    [:> Stack.Screen {:name "Information" :component (fn [props] (r/as-element [information props]))}]
    [:> Stack.Screen {:name "EmailVerify" :component (fn [props] (r/as-element [emailverify props]))}]
    [:> Stack.Screen {:name "EmailSuccess" :component (fn [props] (r/as-element [email-success props]))}]]])

(defn start []
  (expo-root/render-root (r/as-element [root])))

(defn init []
  (start))
