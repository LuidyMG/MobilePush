# MobilePush.
Este é um exemplo de como configurar seu aplicativo Android para receber e exibir notificações enviadas pela ferramenta Mobile Push do Marketing Cloud Salesforce.

### Configurando arquivo project>build.gradle.
  

    buildscript {

        repositórios {
          // Verifique se você tem a seguinte linha (se não, adicione):
          google () // Repositório Maven do Google  
        }

       dependências {
          // ...

          // Adicione a seguinte linha:
          classpath 'com.google.gms:google-services:4.2.0' // plug-in do Google Services  
        }
    }

    allprojects {
        // ...

        repositórios {
          // Verifique se você tem a seguinte linha (se não, adicione):
          google () // Repositório Maven do Google  
          // ...
        }
    }
  
### Configurando arquivo project>app>build.gradle.

    //...

    android {
       //...
       defaultConfig {
          //...
       }
       buildTypes {
          release {
                      //...
          }
       }
    }

    repositories {
        //...
        // Adicione a seguinte linha:
        maven { url "https://salesforce-marketingcloud.github.io/JB4A-SDK-Android/repository" } // Repositório Maven do Marketing Cloud Sdk
    }

    dependencies {
        //...
        // Adicione as seguinte linhas:
        implementation 'com.salesforce.marketingcloud:marketingcloudsdk:6.3.3'
        implementation 'com.google.firebase:firebase-core:16.0.0' // A verção deve ser 16.x.x para a verção 6.3.3 do Marketing Cloud Sdk
        //...
    }

    // Adicione a seguinte linha ao final do arquivo:
    apply plugin: 'com.google.gms.google-services' // Plug-in Gradle do Google Play Services
      
      
### Iniciando o Marketing Cloud Sdk para usar a ferramenta Mobile Push.

###### Local do arquivo: **project>app>src>main>java>com.example.teste_mobile_push2>MainActivity**

    //...

    import com.salesforce.marketingcloud.InitializationStatus;
    import com.salesforce.marketingcloud.MarketingCloudConfig;
    import com.salesforce.marketingcloud.MarketingCloudSdk;
    import com.salesforce.marketingcloud.notifications.NotificationCustomizationOptions;
    import com.salesforce.marketingcloud.registration.RegistrationManager;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //...
            //Definir Chave de Contato.
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
                    //"Código MID" é o Id da organização
                    .setMid("{Seu código MID}")
                    .setNotificationCustomizationOptions(
                            //Icone da notificação
                        NotificationCustomizationOptions.create(R.drawable.ic_notification_icon)
                    )
                    .setGeofencingEnabled(true)
                    .build(this), new MarketingCloudSdk.InitializationListener() {
                @Override public void complete(@NonNull InitializationStatus status) {
                    // TODO handle initialization status
                }
            });
        }
    }
### Dados para configuração do MarketingCloudSdk

#### App Id, Access Token e App Endpoint

![](https://github.com/LuidyMG/MobilePush/blob/master/readme/marketingcloud.jpg)
