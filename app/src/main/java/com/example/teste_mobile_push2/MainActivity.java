package com.example.teste_mobile_push2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.salesforce.marketingcloud.InitializationStatus;
import com.salesforce.marketingcloud.MarketingCloudConfig;
import com.salesforce.marketingcloud.MarketingCloudSdk;
import com.salesforce.marketingcloud.notifications.NotificationCustomizationOptions;
import com.salesforce.marketingcloud.registration.RegistrationManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciar o MarketingCloudSdk para receber as notificações.
        MarketingCloudSdk.init(this, MarketingCloudConfig.builder()
                //Você encontra o "id da sua Aplicação" nas configurações do seu Aplicativo Mobile Push (App ID).
                .setApplicationId("{Seu id da aplicação}")
                //Você encontra o "token de acesso" nas configurações do seu Aplicativo Mobile Push (Access Token).
                .setAccessToken("{Seu token de acesso}")
                //Você encontra o "código de remetente" nas configurações do seu projeto Firebase em Cloud Messaging.
                .setSenderId("{Seu código de remetente}")
                //Você encontra o "ponto de acesso ao Aplicativo" nas configurações do seu Aplicativo Mobile Push (App Endpoint).
                .setMarketingCloudServerUrl("{Seu ponto de acesso ao Aplicativo}")
                //Você encontra o "código MID" nas informações da conta.
                .setMid("{Seu código MID}")
                .setNotificationCustomizationOptions(
                        //Icone da notificação
                    NotificationCustomizationOptions.create(R.drawable.ic_notification_icon)
                )
                .setDelayRegistrationUntilContactKeyIsSet(true)
                .build(this), new MarketingCloudSdk.InitializationListener() {
            @Override public void complete(@NonNull InitializationStatus status) {
                // TODO handle initialization status
            }
        });
        //Definir Chave de .
        MarketingCloudSdk.requestSdk(new MarketingCloudSdk.WhenReadyListener() {
            @Override public void ready(MarketingCloudSdk sdk) {
                RegistrationManager registrationManager = sdk.getRegistrationManager();

                // Inserir sua chave de contato
                registrationManager
                        .edit()
                        //Chave de sua preferencia, contanto que seja único.
                        .setContactKey("{Sua chave de contato}")
                        .commit();
            }
        });
    }
}
