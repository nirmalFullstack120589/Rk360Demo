
## RK360Health App Video
https://github.com/user-attachments/assets/b63b02ee-e169-47bb-ae22-5f494c74b18d

## RK360Health App

**RK360® NAVI**: AI-Powered Healthcare Navigation that Simplifies Your Healthcare Journey
The only HIPAA-compliant, consumer platform with patient-provider matching, data sharing, and care coordination

---


## 🚀 What Makes RK360Health Special?

- **Matching and Communications Nationwide**:Match and communicate with 8M+ providers nationawide – without big-tech surveillance.
- **Data Sharing with Family**: Consolidate, own and share your and your family’s health data in tamper-proof, encrypted cloud       records.
- **Coordination with Record Providers**: Coordinate all your providers with record access that avoids treatment and billing errors.
- **Specific Symptoms Detection**: Give specialists the daily symptoms, images,and lab results needed to solve your medical mysteries. Avoid the cost and harm of needlessly repeated tests.
- **Get Access Via Travelling** Avoid health data breaches and bureaucratic hassles while getting expert care far from home, Lockdown your RK360® Record during travel and find verified experts at your destination

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