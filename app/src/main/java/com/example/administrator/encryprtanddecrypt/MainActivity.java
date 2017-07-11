package com.example.administrator.encryprtanddecrypt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.encryprtanddecrypt.Utils.Aes;
import com.example.administrator.encryprtanddecrypt.Utils.Des;
import com.example.administrator.encryprtanddecrypt.Utils.RSACrypt;

import java.util.Map;

public class MainActivity
        extends AppCompatActivity
{

    private TextView mTv_result;
    private String   data;
    private String   key;
    private boolean  isDes;
    private boolean  isAes;
    private boolean  isRsa;
    private String   mDesEncrypt;
    private String   mAesEncrypt;
    private String   privateKey;
    private String   publicKey;
    private byte[] encryptByPrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_result = (TextView) findViewById(R.id.tv_result);
        data = "我的微信密码： 123456789";//要加密的内容
        key = "123456789";//解密/加密的key
        initKeyPair();
    }

    /**
     * 初始化秘钥对
     */
    private void initKeyPair() {
        // 初始化秘钥对： 公钥和私钥
        try {
            Map<String, Object> keyPair = RSACrypt.genKeyPair();
            privateKey = RSACrypt.getPrivateKey(keyPair);
            publicKey = RSACrypt.getPublicKey(keyPair);

            Log.e("result", "privateKey=" + privateKey);
            Log.e("result", "publicKey=" + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void des(View view) {
        try {
            if (!isDes) {
                //加密
                mDesEncrypt = Des.encrypt(data, key);
                mTv_result.setText("DES加密：" + mDesEncrypt);
            } else {
                String desDecrypt = Des.decrypt(mDesEncrypt, key);//解密
                mTv_result.setText("DES解密：" + desDecrypt);
            }
            isDes = !isDes;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aes(View view) {
        if (!isAes) {
            //加密
            mAesEncrypt = Aes.encrypt(data, key);
            mTv_result.setText("AES加密：" + mAesEncrypt);
        } else {
            //解密
            String aesEecrypt = Aes.decrypt(mAesEncrypt, key);
            mTv_result.setText("AES解密：" + aesEecrypt);
        }
        isAes = !isAes;
    }

    public void rsa(View view) {
        try {
            if (!isRsa) {
                //私钥加密
                encryptByPrivateKey = RSACrypt.encryptByPrivateKey(data.getBytes(), privateKey);
                mTv_result.setText("RSA加密："+RSACrypt.encode(encryptByPrivateKey));
            }else {
                //公钥解密
                byte[] decryptByPublicKey = RSACrypt.decryptByPublicKey(encryptByPrivateKey, publicKey);
                mTv_result.setText("RSA解密："+new String(decryptByPublicKey));
            }
            isRsa=!isRsa;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
