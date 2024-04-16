<div align="center">
    <img width="200" height="200" style="display: block; border: 1px solid #f5f5f5; border-radius: 9999px;" src="https://github.com/6eero/NewPass/assets/114809573/77aeeea8-5440-433b-8621-2a5b54173896">
</div>

<div align="center">
    <h1>NewPass</h1>
</div>

<div align="center">
    <a href="LICENSE">
        <img src="https://img.shields.io/github/license/6eero/newpass.svg?color=D0BCFF&style=for-the-badge&logo=gitbook&logoColor=ebebf0&labelColor=23232F" alt="License">
    </a>
    <a href="https://github.com/6eero/NewPass/releases">
        <img src="https://img.shields.io/github/downloads/6eero/NewPass/total.svg?color=D0BCFF&style=for-the-badge&logo=github&logoColor=ebebf0&labelColor=23232F" alt="GitHub release">
    </a>
    <a href="https://github.com/6eero/NewPass/stargazers">
        <img src="https://img.shields.io/github/stars/6eero/NewPass.svg?color=D0BCFF&style=for-the-badge&logo=apachespark&logoColor=ebebf0&labelColor=23232F" alt="Stars Count">
    </a>
</div>
<div align="center">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/6eero/NewPass?color=D0BCFF&style=for-the-badge&logo=github&logoColor=ebebf0&labelColor=23232F">
    <a href="https://github.com/6eero/NewPass/releases">
        <img src="https://img.shields.io/github/v/release/6eero/NewPass?color=D0BCFF&style=for-the-badge&logo=pkgsrc&logoColor=ebebf0&labelColor=23232F" alt="GitHub release">
    </a>
</div>
<br>
<p align="right">
   <img src="https://github.com/6eero/NewPass/assets/114809573/72a2d172-8b44-4bf9-bc70-573284ea1e1a" title="UI">
</p>
<br>


# 📍Intro
NewPass is a secure password management application designed to generate and store strong passwords locally on your device. With NewPass, you can create highly secure passwords for your accounts and services without the need to remember them.
> To support our free open source project, please give it a star. ⭐
> This means a lot to us. Thank you so much! [![GitHub Repo stars](https://img.shields.io/github/stars/6eero/NewPass?style=social)](https://github.com/6eero/NewPass/stargazers)
<br>

## 🗝️ Key Features:
- **Password Generation**: NewPass provides a robust password generator that allows you to create complex and secure passwords tailored to your specific requirements. You can customize the length and the character set (Uppercase, Numbers and Special).

- **Local Storage**: Your passwords are stored locally on your device, ensuring complete privacy and control over your data. NewPass does not store any passwords on external servers, minimizing the risk of unauthorized access (If you uninstall the app, your password are lost!).

- **AES Encryption**: NewPass encrypts all stored passwords using Advanced Encryption Standard (AES) with Cipher Block Chaining (CBC) mode before saving them in the local database.

- **SQLite Chiper**: NewPass utilizes SQLCipher, an extension for SQLite databases, to bolster security further by encrypting entirely the database, ensuring robust protection against unauthorized access. The encryption key is chosen by the user upon the first launch of the app, and it remains saved and encrypted in an EncryptedSharedPreferences. It is then requested every time the app is launched. 

- **User-Friendly Interface**: NewPass features an intuitive and user-friendly interface, making it easy to generate, view, and manage your passwords. The app offers convenient options for copying passwords to the clipboard and securely sharing them with other applications.
<br>

## 📸 App Screenshots
<table>
  <tr>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/17e31b41-2da1-455e-940b-ab41342ecb93" alt="Screenshot 1"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/638a1b08-3fe4-46d0-82c8-7e1ed29c3082" alt="Screenshot 2"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/b07d9717-65a3-4c2a-bb8f-ecaf7d94516f" alt="Screenshot 3"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/2751be07-78ab-4854-b929-bfcf20a98d91" alt="Screenshot 4"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/14c5866e-a251-42f6-9560-dafcacab0f14" alt="Screenshot 5"></td>
  </tr>
</table>
<br>

## ⬇️ Download 
[<img src="https://s1.ax1x.com/2023/01/12/pSu1a36.png" alt="Get it on GitHub" height="80">](https://github.com/6eero/NewPass/releases)
[<img src="https://github.com/6eero/NewPass/assets/114809573/113b2ce8-fd57-490e-bce0-9db1e55f52ba" alt="Get it on aaaa" height="80">](https://apt.izzysoft.de/fdroid/index/apk/com.gero.newpass/)
<br>

## 🧱 Build
1. First you need to get the source code of NewPaass.
```
git clone https://github.com/6eero/NewPass.git
```
2. Open the project in [Android Studio](https://developer.android.com/studio).
3. When you click the `▶ Run` button, it will be built automatically.
4. Launch NewPass.
<br>

## 🫱🏻‍🫲🏼 Contributions and Development
If you wish to contribute to the NewPass project or report bugs, we encourage you to do so through the GitHub issue system. I accept and appreciate community contributions to continually improve the application.

### Contributors Wall:

<a href="https://github.com/6eero/NewPass/graphs/contributors">
  <img alt="contributors graph" src="https://contrib.rocks/image?repo=6eero/NewPass" />
</a>
<br>

### Need help?

[![Telegram chat](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/geroed)
<br>
<br>

## ⚒️ To-do
- [ ] Allow exporting/importing backups.
- [x] Implement login to NewPass with device lock screen credentials.
- [x] Add a vibration feedback.
- [x] Implement dark theme.
- [x] Add support for more languages.
<br>

## 📜 License
The NewPass project is distributed under the open-source GNU GPL v3.0 ©. Please refer to the [LICENSE](https://github.com/6eero/NewPass/blob/master/LICENSE) file for more information on the license.
<br>
<br>

## 💰 Buy me a coffe
BTC: bc1qlkm4wgtr0z3y72w9ag90vyejy895fc3fj3djtx or [PayPal](https://www.paypal.com/paypalme/geeero)
<br>
<br>

<div align="right">
<table><td> 
<a href="#start-of-content">👆 Scroll to top</a>
</td></table> 
</div>
