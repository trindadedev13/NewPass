# Reporting a Vulnerability

If you believe you have found a security vulnerability in our software, we encourage you to let us know right away. We will investigate all legitimate reports and do our best to quickly fix the problem.

## Private Contact Method

To securely send a message to the `security@newpass.solutions` email address, you can encrypt your message using the GPG key provided by NewPass.

### Step 1: Download the GPG Public Key

First, download the GPG public key from the following link:
[NewPass Public Key](https://github.com/6eero/NewPass/blob/master/public_key.asc)

### Step 2: Import the Public Key

Once the public key is downloaded, import it into your GPG keyring using the following command in your terminal or command prompt:

```
gpg --import public_key.asc
```

### Step 3: Verify Key Import
Verify that the public key has been imported successfully by running:

```
gpg --list-keys
```

You should see the NewPass public key listed among your imported keys.

### Step 4: Encrypt Your Message

Now, you can encrypt your message using the NewPass public key. For example, if your message is stored in a file called message.txt, you can use the following command:

```
gpg --encrypt --armor -r security@newpass.solutions < message.txt > encrypted_message.asc
```

This command encrypts your message and saves the encrypted output to a file named encrypted_message.asc.

### Step 5: Send Encrypted Message
You can now send the encrypted message file (encrypted_message.asc) as an email attachment to security@newpass.solutions. 

## Contact Information

For general inquiries or assistance, please open an issue or follow [CONTRIBUTING.md](https://github.com/6eero/NewPass/blob/master/CONTRIBUTING.md).

