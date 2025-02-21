
## RK360Health App Video
https://github.com/user-attachments/assets/b63b02ee-e169-47bb-ae22-5f494c74b18d

## RK360Health App

**RK360® NAVI Sign Up Flow**: This App contains the full Sign up flow for registering user via number and email

---


## 🛠 How Does It Work?

RK360Health is built with:

- **React Native** with **ClojureScript** for a seamless, cross-platform experience.
- **Storing Token** powered by `@react-native-async-storage/async-storage` to fetch your tokens and storing data in one go.
- **Clean Architecture**: Organized and efficient code for better performance and scalability.
- **RK360Health**: (https://rk360.health/)

## 🛠 Setting Up RK360Health App

Ready to check out the RK360Health App at one place? Let’s get you started:

### Prerequisites

1. Make sure you’ve efficient setup to run ClojureScript + React Native project.

---

## Installation

# Install clojure

```
brew install clojure
```

# Install node dependencies

```
npm install
```
- For iOS:

&nbsp;&nbsp;&nbsp;&nbsp;Open the ios/Podfile, ensure dependencies are updated, and run:

```bash
cd ios
pod install
cd ..
```

3. Run the App:

- On **iOS**:

```bash
npx react-native run-ios
```

- On **Android**:

```bash
npx react-native run-android
```

# Compile and run shadow-cljs

```
npx shadow init
npx shadow-cljs compile app
npx shadow-cljs watch app
```

# Start the expo server alongside with the shadow-cljs terminal

```
npx expo start
```

and start expo on your desired Platform

---