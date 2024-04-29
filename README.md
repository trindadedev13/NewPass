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
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/9cf382a4-1eff-4ab4-a91d-7654e300ebae" alt="Screenshot 1"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/d3065b87-5393-4a72-9a7f-8bee75e04c7f" alt="Screenshot 2"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/60d88e97-4ad8-4da2-9aa7-208d0afd69c5" alt="Screenshot 3"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/c611b2fb-975e-44e5-a47a-c61a3592b953" alt="Screenshot 4"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/c6d05c7f-3d80-4962-a711-aef95d6f286d" alt="Screenshot 5"></td>
  </tr>
  <!-- Nuova riga aggiunta sotto -->
  <tr>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/52c4e34f-e462-4c16-ad7c-d96e94666521" alt="Screenshot 6"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/cfc3eb38-0322-4914-9aaa-accab8f9f4f3" alt="Screenshot 7"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/4df32579-de49-4b6c-9758-12b60b1da108" alt="Screenshot 8"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/eceda8b0-ef6f-4e28-aabe-9ff32abd9e6c" alt="Screenshot 9"></td>
    <td><img src="https://github.com/6eero/NewPass/assets/114809573/2d0f1969-71be-4013-89bd-cc586484e693" alt="Screenshot 10"></td>
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
If you wish to contribute to the NewPass project or report bugs, see the Instructions in [CONTRIBUTING.md](https://github.com/6eero/NewPass/blob/master/CONTRIBUTING.md).
I accept and appreciate community contributions to continually improve the application.

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
- [x] Add an option to change the app password.
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
