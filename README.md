# 🥩 MeatMetric

An Android app that helps users calculate the ideal amount of meat for a barbecue, considering the audience type, presence of drinks and side dishes, and sharing the total cost among participants. Built to ensure accurate planning, no waste, and a smooth experience for every grill master.

---

## 🎯 Inspiration Behind the Name

**MeatMetric** is a blend of “Meat” (the central item in a barbecue) and “Metric” (a system for measurement), representing the app’s goal: **smart and accurate meat calculation** for group events. The name highlights the practicality and precision needed for organizing great barbecues.

---

## 🩹 Problem We're Solving

Planning a barbecue often leads to overbuying or underestimating the quantity of meat, especially when people of different profiles are involved (men, women, children). On top of that, splitting the cost fairly can be messy.  
**MeatMetric** solves this by providing:
- 🎯 Personalized meat calculations based on real-world barbecue profiles  
- 💰 An easy cost-sharing tool (rateio)  
- 🧾 Option to attach the purchase receipt  
- 📤 Sharing functionality to keep everyone informed  

---

## ✅ Features

- 🧮 Personalized meat quantity based on audience type: men, women, mixed, children  
- 🍻 Adjustments for presence of side dishes and alcoholic beverages  
- 🎨 Dark theme with white text and red buttons (#2D333A background)  
- 💸 Rateio screen:
  - Enter total cost
  - Define number of contributors
  - View per-person value
  - Attach purchase receipt (optional)
  - Share via external apps
- 👤 Profile screen:
  - Shows name, email, and account creation date
  - Uses Room for local storage
- 🔐 SessionManager for persisting user data across the app
- 🖼️ Branded splash screen at app startup  

---

## 🧪 Requirements

- Android Studio (recommended: Hedgehog or newer)  
- Minimum Android SDK: 24  
- Room Persistence Library  
- Internet permission (if sharing is used)

---

## 🖼️ Screenshots

> (Add your screenshots here to show Splash, Calculation Result, Rateio screen, and Profile screen)

---

## 🚀 How to Use

1. Launch the app  
2. Select audience type and optional settings (sides, drinks)  
3. Tap **Calculate** to get the ideal meat quantity  
4. View detailed results and tap **Rateio** to split the cost  
5. Attach a receipt (optional) and share with others  
6. Access your profile anytime through the side menu  

---

## 🧱 Main Screens

- `SplashActivity`: App launch screen  
- `MainActivity`: Parameter input screen  
- `ResultadoActivity`: Result screen with meat calculation  
- `RateioActivity`: Cost-sharing screen  
- `PerfilActivity`: User profile and logout  
- `SessionManager`: Manages session and user info  
- `RoomDatabase`: Stores user data locally  

---

## 🛠️ Technologies Used

- Java  
- MVVM Architecture  
- Room (Local persistence)  
- Custom dark theme  
- Glide (for receipt image preview)  
- Intent Sharing (to share rateio result)  

---

## 📁 Local Storage Structure (Room)

- `UsuarioEntity`
  - Fields: `id`, `nome`, `email`, `dataCriacao`  

- Stored locally on the device and used in profile display

---

## 📌 Notes

- All calculations are done locally  
- Data like name and email are saved on-device using Room  
- Designed with accessibility and visual consistency in mind  
- Ideal for personal and group barbecue planning  

---

## 🔐 Security and Privacy

No sensitive data is stored online. All user information remains on the local device. Sharing is optional and triggered by the user.

---

## 📄 License

This project is intended for educational and personal use.  
For commercial usage or contributions, please contact the author.

