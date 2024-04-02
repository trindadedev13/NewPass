<div align="center">
    <img width="200" height="200" style="display: block; border: 1px solid #f5f5f5; border-radius: 9999px;" src="https://github.com/6eero/NewPass/assets/114809573/77aeeea8-5440-433b-8621-2a5b54173896">
</div>

<div align="center">
    <h1>NewPass</h1>
</div>

<div align="center">
    <img alt="GitHub" src="https://img.shields.io/github/license/Ashinch/ReadYou?color=D0BCFF&style=flat-square">
    <img alt="Version" src="https://img.shields.io/github/v/release/6eero/NewPass?color=D0BCFF&label=version&style=flat-square">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/6eero/NewPass?color=D0BCFF&style=flat-square">
</div>

<br>

<p align="right">
   <img src="https://github.com/6eero/NewPass/assets/114809573/72a2d172-8b44-4bf9-bc70-573284ea1e1a" title="UI">
</p>

<br>

# 📍Intro
NewPass is a secure password management application designed to generate and store strong passwords locally on your device. With NewPass, you can create highly secure passwords for your accounts and services without the need to remember them.


## 🗝️ Key Features:
- **Password Generation**: NewPass provides a robust password generator that allows you to create complex and secure passwords tailored to your specific requirements. You can customize the length and the character set (Uppercase, Numbers and Special).

- **Local Storage**: Your passwords are stored locally on your device, ensuring complete privacy and control over your data. NewPass does not store any passwords on external servers, minimizing the risk of unauthorized access (If you uninstall the app, your password are lost!).

- **AES Encryption**: NewPass encrypts all stored passwords using Advanced Encryption Standard (AES) with Cipher Block Chaining (CBC) mode before saving them in the local database.

- **SQLite Chiper**: NewPass utilizes SQLCipher, an extension for SQLite databases, to bolster security further by encrypting entirely the database, ensuring robust protection against unauthorized access. The encryption key is chosen by the user upon the first launch of the app, and it remains saved and encrypted in an EncryptedSharedPreferences. It is then requested every time the app is launched. 

- **User-Friendly Interface**: NewPass features an intuitive and user-friendly interface, making it easy to generate, view, and manage your passwords. The app offers convenient options for copying passwords to the clipboard and securely sharing them with other applications.

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

## ⬇️ Download 
[<img src="https://s1.ax1x.com/2023/01/12/pSu1a36.png" alt="Get it on GitHub" height="80">](https://github.com/6eero/NewPass/releases)
[<img src="https://github.com/6eero/NewPass/assets/114809573/113b2ce8-fd57-490e-bce0-9db1e55f52ba" alt="Get it on aaaa" height="80">](https://apt.izzysoft.de/fdroid/index/apk/com.gero.newpass/)


## 🧱 Build
1. First you need to get the source code of NewPaass.
```
git clone https://github.com/6eero/NewPass.git
```
2. Open the project in [Android Studio](https://developer.android.com/studio).
3. When you click the `▶ Run` button, it will be built automatically.
4. Launch NewPass.

## 🫱🏻‍🫲🏼 Contributions and Development
If you wish to contribute to the NewPass project or report bugs, we encourage you to do so through the GitHub issue system. I accept and appreciate community contributions to continually improve the application.

## ⚒️ Todo
- [x] Refactor code to use MVVM design pattern.
- [x] Fix small screen layout display issue.
- [x] Prevent adding duplicate entries to the database.
- [x] Set maximum and minimum length constraints on fields before inserting/updating entries in the database.
- [x] Fix random crashes with multiple users.
- [x] Improve the login GUI.
- [x] Add a login menu to unlock the application.
- [x] Currently the encryption key and IV vector are hardcoded into the source code. I have to find a way to mask them.
- [x] Add encryption: The app does not currently provide secrecy restrictions on entered passwords.

## 📜 License
The NewPass project is distributed under the open-source GNU GPL v3.0 ©. Please refer to the [LICENSE](https://github.com/6eero/NewPass/blob/master/LICENSE) file for more information on the license.
