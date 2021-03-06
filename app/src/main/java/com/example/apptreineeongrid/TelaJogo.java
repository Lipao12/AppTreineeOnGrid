package com.example.apptreineeongrid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {

    private TelaJogo.ViewHolder mViewHolder = new TelaJogo.ViewHolder();
    private ArrayList<String> perguntas;
    private ArrayList<String> resp_errada;
    private ArrayList<String> resp_correta;
    private ArrayList<String> curiosidades;
    public ArrayList<Integer> pergs_fazer;
    private ArrayList<Integer> botoes_acertar;
    private int x;
    private int i;
    private int num_pergunta;
    public int score;
    private static Boolean rClicado=true;
    private Typeface fonte;
    private int perg_estacionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        this.mViewHolder.r1 = findViewById(R.id.Opc1);
        this.mViewHolder.r2 = findViewById(R.id.Opc2);
        this.mViewHolder.r3 = findViewById(R.id.Opc3);
        this.mViewHolder.b_continuar = findViewById(R.id.continuar);
        this.mViewHolder.b_vcSabia = findViewById(R.id.vcSabia);
        this.mViewHolder.pergunta_texto = findViewById(R.id.pergunta);
        this.mViewHolder.imagem_perguntas = findViewById(R.id.imageView);
        this.mViewHolder.score_texto = findViewById(R.id.score);
        this.mViewHolder.rosto_feliz = findViewById(R.id.rosto_feliz);
        this.mViewHolder.rosto_triste = findViewById(R.id.rosto_triste);

        /*Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, android.R.drawable.toast_frame,null);*/

        perguntas = new ArrayList<>();
        resp_errada = new ArrayList<>();
        resp_correta = new ArrayList<>();
        curiosidades = new ArrayList<>();
        pergs_fazer = new ArrayList<>();
        botoes_acertar = new ArrayList<>();

        mudarFonte();

       // final boolean[] jogoAcabou = {false};
        char dificuldade = getIntent().getExtras().getChar("dificuldade");

        System.out.println("Dificuldade: "+dificuldade);
        if(dificuldade != '\0' )
        {
            mViewHolder.rosto_triste.setVisibility(View.GONE);
            mViewHolder.rosto_feliz.setVisibility(View.GONE);
            mViewHolder.b_continuar.setVisibility(View.GONE);
            mViewHolder.b_vcSabia.setVisibility(View.GONE);

            perg_estacionada=1;
            score=0;

            inicializarPerguntas(dificuldade);
            for (x=0;x<perguntas.size();x++)
            {
                pergs_fazer.add(x);
            }
            for (i=0;i<3;i++)
            {
                botoes_acertar.add(i);
            }
            dificuldade = '\0';
            gerarPerguntasAleatorias();
            imprimirQuantidadeDePergunta();
        }

        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.r1.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r2.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r3.setBackgroundColor(0xFFFFFFFF);

                mViewHolder.rosto_triste.setVisibility(View.GONE);
                mViewHolder.rosto_feliz.setVisibility(View.GONE);
                mViewHolder.b_continuar.setVisibility(View.GONE);
                mViewHolder.b_vcSabia.setVisibility(View.GONE);
                mViewHolder.pergunta_texto.setVisibility(View.VISIBLE);
                mViewHolder.imagem_perguntas.setVisibility(View.VISIBLE);
                imprimirQuantidadeDePergunta();
               // if(!jogoAcabou[0])
                    gerarPerguntasAleatorias();
               // else
               // {
             //       Intent intent = new Intent(TelaJogo.this,TelaFinal.class);
               //     startActivity(intent);
              //  }
            }
        });

        mViewHolder.b_vcSabia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this,TelaCuriosidade.class);
                intent.putExtra("curiosidade",curiosidades.get(num_pergunta));
                startActivity(intent);
            }
        });


        // VALIDAR RESPOSTA
        mViewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r1.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText("/"+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r1.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                      //  jogoAcabou[0] = true;
                    }
                }
            }
        });
        mViewHolder.r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r2.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r2.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                       // jogoAcabou[0] = true;
                    }
                }
            }
        });
        mViewHolder.r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r3.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r3.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                       // jogoAcabou[0] = true;
                    }
                }
            }
        });
    }

    public static class ViewHolder{
        Button r1;
        Button r2;
        Button r3;
        Button b_continuar;
        Button b_vcSabia;
        TextView pergunta_texto;
        TextView score_texto;
        ImageView imagem_perguntas;
        ImageView rosto_feliz;
        ImageView rosto_triste;
    }

    public void gerarPerguntasAleatorias()
    {
          rClicado = false;

        //VALIDAR PERGUNTA
        if (pergs_fazer.size() > 0) {
            x = new Random().nextInt(pergs_fazer.size());
            num_pergunta = pergs_fazer.get(x);

            //GERAR LUGARES DE RESPOSTA ALEATORIO
            i = new Random().nextInt(botoes_acertar.size());
            if (i == 1) {
                mViewHolder.r1.setText(resp_correta.get(num_pergunta));
                mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
            } else if (i == 2) {
                mViewHolder.r2.setText(resp_correta.get(num_pergunta));
                mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
            } else {
                mViewHolder.r3.setText(resp_correta.get(num_pergunta));
                mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta + 1));
            }

            pergs_fazer.remove(x);
        } else if (pergs_fazer.size() == 0) {
            Intent intent = new Intent(TelaJogo.this,TelaFinal.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        mViewHolder.pergunta_texto.setText(perguntas.get(num_pergunta));
    }

    public static void set_rClicado(boolean clicado)
    {
        rClicado=clicado;
    }

    public void imprimirQuantidadeDePergunta()
    {
        mViewHolder.score_texto.setText(perg_estacionada+"/"+perguntas.size());
        perg_estacionada++;
    }

    public void acharRespCorreta()
    {
        if(resp_correta.get(num_pergunta) == mViewHolder.r1.getText())
        {
            mViewHolder.r1.setBackgroundColor(0xD806B50D);
        }
        else if(resp_correta.get(num_pergunta) == mViewHolder.r2.getText())
        {
            mViewHolder.r2.setBackgroundColor(0xD806B50D);
        }
        else
        {
            mViewHolder.r3.setBackgroundColor(0xD806B50D);
        }
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");
        mViewHolder.pergunta_texto.setTypeface(fonte);
        mViewHolder.r1.setTypeface(fonte);
        mViewHolder.r2.setTypeface(fonte);
        mViewHolder.r3.setTypeface(fonte);
    }

    private void inicializarPerguntas(char dificuldade) // FOI COLOCADA ATE A PERGUNTA 20
    {
        if(dificuldade == 'f')
        {
            perguntas.add("Qual ?? a camada mais externa do Sol?");
            curiosidades.add("A estrutura do Sol ?? composta pelas principais regi??es: n??cleo, zona radiativa, zona convectiva, fotosfera, cromosfera e coroa.");
            resp_correta.add("Coroa");
            resp_errada.add("Fotosfera");
            resp_errada.add("N??cleo");

            perguntas.add("Qual elemento qu??mico mais abundante no Sol?");
            curiosidades.add("O Sol ?? constitu??do em sua maioria de Hidrog??nio (91,2%), mas tamb??m possui H??lio (8,7%), Oxig??nio(0,078%) e Carbono(0,043%)");
            resp_correta.add("H");
            resp_errada.add("Fe");
            resp_errada.add("He");

            perguntas.add("Qual regi??o ?? respons??vel pela maior parte da radia????o vis??vel emitida pelo Sol?");
            curiosidades.add("A fotosfera, primeira regi??o da atmosfera solar, com 330 km de espessura e temperatura pr??xima de 5.800 K.");
            resp_correta.add("Fotosfera");
            resp_errada.add("N??cleo");
            resp_errada.add("Cromosfera");

            perguntas.add("Qual a pot??ncia total disponibilizada pelo Sol ?? Terra?");
            curiosidades.add("A densidade m??dia anual do fluxo energ??tico proveniente da radia????o solar (irradi??ncia solar) recebe o nome de ???constante solar??? e corresponde ao valor de 1.367 W/m2 . Considerando que o raio m??dio da Terra ?? 6.371 km, conclui-se que a pot??ncia total disponibilizada pelo Sol ?? Terra ?? de aproximadamente 174 mil TW (terawatts)");
            resp_correta.add("174 mil Terawatts");
            resp_errada.add("45 Kilowatts");
            resp_errada.add("152 mil Megawatts");

            perguntas.add("Parte da pot??ncia total disponibilizada pelo Sol ?? Terra ?? absorvida ou refletida pela atmosfera. Quantos % dessa pot??ncia chegam ?? superf??cie terrestre?");
            curiosidades.add("Cerca de 54 % da irradi??ncia solar que incide no topo da atmosfera, ?? refletida (7 %) e absorvida (47 %) pela superf??cie terrestre (os 46 % restantes s??o absorvidos ou refletidos diretamente pela atmosfera). Ou seja, da pot??ncia total disponibilizada pelo Sol ?? Terra, cerca de 94 mil TW chegam efetivamente ?? superf??cie terrestre.");
            resp_correta.add("54%");
            resp_errada.add("76%");
            resp_errada.add("28%");

            perguntas.add("Qual foi a primeira Usina Fotovoltaica implantada no Brasil?");
            curiosidades.add("A primeira UFV implantada no Brasil foi um empreendimento privado, da empresa MPX, localizado no munic??pio de Tau??-CE, a cerca de 360 km de Fortaleza. A UFV Tau?? entrou em opera????o em julho de 2011 e tem pot??ncia instalada de 1,0 MWp, em 4.680 m??dulos de p-Si de 215Wp, conta com 9 inversores de 100kWp e injeta a energia na rede de 13,8 kV da Coelce (Companhia Energ??tica do Cear??).");
            resp_correta.add("Usina solar de Tau??");
            resp_errada.add("Complexo solar Lapa");
            resp_errada.add("Usina solar S??o Gon??alo");

            perguntas.add("Quando foi observado pela primeira vez o Efeito fotovoltaico?");
            curiosidades.add("O efeito fotovoltaico foi observado pela primeira vez em 1839 por Edmond Becquerel que verificou que placas met??licas, de platina ou prata, mergulhadas num eletr??lito, produziam uma pequena diferen??a de potencial quando expostas ?? luz.");
            resp_correta.add("1839");
            resp_errada.add("1996");
            resp_errada.add("1742");

            perguntas.add("Quando ocorreu a primeira constru????o da c??lula solar?");
            curiosidades.add("A hist??ria da primeira c??lula solar come??ou em Mar??o de 1953 quando Calvin Fuller, um qu??mico dos Bell Laboratories (Bell Labs), em Murray Hill, New Jersey, nos EUA, desenvolveu um processo de difus??o para introduzir impurezas em cristais de sil??cio, de modo a controlar as suas propriedades el??ctricas.");
            resp_correta.add("1953");
            resp_errada.add("1998");
            resp_errada.add("1945");

            perguntas.add("A primeira demonstra????o da c??lula solar foi:");
            curiosidades.add("Nas p??ginas do New York Times podia ler-se que aquela primeira c??lula solar ???marca o princ??pio de uma nova era, levando, eventualmente, ?? realiza????o de um dos mais belos sonhos da humanidade: a colheita de energia solar sem limites, para o bem-estar da civiliza????o???");
            resp_correta.add("Em uma transmiss??o via r??dio");
            resp_errada.add("Para ligar uma televis??o");
            resp_errada.add("Para acender uma l??mpada");

            perguntas.add("Qual foi a primeira aplica????o das c??lulas solares?");
            curiosidades.add("A primeira aplica????o das c??lulas solares de Chapin, Fuller e Pearson foi realizada em Americus, no estado da Georgia, para alimentar uma rede telef??nica local ");
            resp_correta.add("Alimentar um rede telef??nica ");
            resp_errada.add("Em uma lanterna");
            resp_errada.add("Em uma m??quina de costura");

            perguntas.add("Qual a principal dificuldade reconhecida no passado e presente at?? os dias atuais em rela????o a instala????o de m??dulos solares?");
            curiosidades.add("Uma das primeiras dificuldades encontradas e compreendidas foi que o custo das c??lulas solares era demasiado elevado, pelo que a sua utiliza????o s?? podia ser economicamente competitiva em aplica????es muito especiais, como, por exemplo, para produzir eletricidade no espa??o.");
            resp_correta.add("Custo elevado dos m??dulos solares");
            resp_errada.add("Necessidade de troca de pe??as mensalmente");
            resp_errada.add("Falta de profissionais adequados");

            perguntas.add("A primeira oportunidade de uso da energia solar foi:");
            curiosidades.add("Para uso inicial de produ????o de eletricidade no espa??o, os sat??lites usaram pilhas qu??micas ou baseadas em is??topos radioativos. As c??lulas solares eram consideradas uma curiosidade, e foi com grande relut??ncia que a NASA aceitou incorpor??-las");
            resp_correta.add("No espa??o");
            resp_errada.add("Nas resid??ncias");
            resp_errada.add("Em pra??as p??blicas");

            perguntas.add("Quais pa??ses mais utilizam energia solar?");
            curiosidades.add("Os pa??ses mais desenvolvidos no aproveitamento da energia solar s??o a Alemanha, a It??lia, o Jap??o, a Espanha e os Estados Unidos. Esses pa??ses promoveram programas para incentivar a utiliza????o dos sistemas fotovoltaicos.");
            resp_correta.add("Alemanha, Jap??o e EUA");
            resp_errada.add("Brasil, R??ssia e Jap??o");
            resp_errada.add("Jap??o, Espanha e China");

            perguntas.add("Qual o estado brasileiro que possui a maior pot??ncia de energia solar instalada?");
            curiosidades.add("");
            resp_correta.add("Minas Gerais");
            resp_errada.add("S??o Paulo");
            resp_errada.add("Rio de Janeiro");

            perguntas.add("No Brasil o principal uso da energia solar ??:");
            curiosidades.add("A energia solar est?? sendo utilizada no Brasil majoritariamente em resid??ncias, como uma auxiliar na redu????o da conta de luz, seja por meio da energia t??rmica, aquecendo ??gua, ou com a utiliza????o da energia fotovoltaica, gerando eletricidade.");
            resp_correta.add("Nas resid??ncias");
            resp_errada.add("Em postes de luz");
            resp_errada.add("Nas ind??strias");

            perguntas.add("O que incentiva as pessoas a instalarem sistemas fotovoltaicos no Brasil?");
            curiosidades.add("Com a redu????o progressiva dos custos, o aumento do rendimento dos sistemas solares, e a eleva????o das tarifas das concession??rias de distribui????o de energia, a paridade de custo final da energia produzida pelos sistemas fotovoltaicos e das tarifas das concession??rias j?? ?? uma realidade, o que incentiva a autoprodu????o de energia.");
            resp_correta.add("Foco na redu????o da conta de energia el??trica");
            resp_errada.add("Foco na quest??o ambiental");
            resp_errada.add("Foco em status social");

            perguntas.add("Qual dessas n??o ?? uma vantagem da energia Solar?");
            curiosidades.add("O custo inicial para montar um sistema solar ?? bastante avultado, devido aos equipamentos.");
            resp_correta.add("Alto custo de instala????o");
            resp_errada.add("?? renov??vel e gratuita");
            resp_errada.add("N??o ocupam espa??o f??sico");

            perguntas.add("Qual dessas n??o ?? uma desvantagem da energia Solar?");
            curiosidades.add("Embora os equipamentos solares exijam um investimento inicial mais avultado, esse investimento ?? recuperado, gra??as ao dinheiro economizado nas contas de eletricidade, ??gua e g??s.");
            resp_correta.add("Os investimentos de instala????o ?? recuperado com o passar do tempo");
            resp_errada.add("Se n??o houver sol, n??o haver?? produ????o de energia");
            resp_errada.add("Necessidade de estar conectado ?? rede ou possuir armazenamento durante a noite");

        }

        else if(dificuldade == 'm')
        {
            perguntas.add("Qual o elemento mais utilizado na fabrica????o de c??lulas fotovoltaicas?");
            curiosidades.add("Os ??tomos de Si s??o tetravalentes, ou seja, caracterizam-se por possu??rem 4 el??trons de val??ncia que formam liga????es covalentes com os ??tomos vizinhos, resultando em 8 el??trons compartilhados por cada ??tomo, constituindo uma rede cristalina.");
            resp_correta.add("Sil??cio");
            resp_errada.add("Ars??nio");
            resp_errada.add("G??lio");

            perguntas.add("Uma associa????o em s??rie dos m??dulos tem como objetivo uma soma de qual grandeza el??trica?");
            curiosidades.add("Na conex??o em s??rie, o terminal positivo de um dispositivo fotovoltaico ?? conectado ao terminal negativo do outro dispositivo, e assim por diante. Para dispositivos id??nticos e submetidos ?? mesma irradi??ncia, quando a liga????o ?? em s??rie, as tens??es s??o somadas e a corrente el??trica n??o ?? afetada, ou seja:\n" +
                    "V=V1+V2+....+Vn \n" +
                    "I=I1=I2=....=In ");
            resp_correta.add("Tens??o");
            resp_errada.add("Corrente");
            resp_errada.add("Pot??ncia");

            perguntas.add("Uma associa????o em paralelo dos m??dulos tem como objetivo uma soma de qual grandeza el??trica?");
            curiosidades.add("Na associa????o em paralelo, os terminais positivos dos dispositivos s??o interligados entre si, assim como os terminais negativos. As correntes el??tricas s??o somadas, permanecendo inalterada a tens??o. Ou seja:\n" +
                    "I=I1+I2+...+In\n" +
                    "V=V1=V2=...=Vn");
            resp_correta.add("Corrente");
            resp_errada.add("Tens??o");
            resp_errada.add("Pot??ncia");

            perguntas.add("Em um sistema fotovoltaico h?? um componente respons??vel por converter uma corrente cont??nua (c.c.) em corrente alternada (c.a.). Que componente ?? esse?");
            curiosidades.add("Um inversor ?? um dispositivo eletr??nico que fornece energia el??trica em corrente alternada (c.a.) a partir de uma fonte de energia el??trica em corrente cont??nua (c.c.). A tens??o c.a. de sa??da deve ter amplitude, frequ??ncia e conte??do harm??nico adequados ??s cargas a serem alimentadas.");
            resp_correta.add("Inversores");
            resp_errada.add("Baterias");
            resp_errada.add("Controladores de carga");

            perguntas.add("Em qual sistema fotovoltaico ?? indispens??vel o armazenamento de energia?");
            curiosidades.add("Sistemas isolados (SFI), em geral, necessitam de algum tipo de armazenamento. O armazenamento pode ser em baterias, quando se deseja utilizar aparelhos el??tricos nos per??odos em que n??o h?? gera????o fotovoltaica, ou em outras formas de armazenamento de energia.");
            resp_correta.add("Sistemas isolados");
            resp_errada.add("Sistema de bombeamento");
            resp_errada.add("Sistemas Conectados ?? Rede");

            perguntas.add("Qual a densidade m??dia anual do fluxo energ??tico proveniente da radia????o solar?");
            curiosidades.add("As c??A densidade m??dia anual do fluxo energ??tico proveniente da radia????o solar (irradi??ncia solar), quando medida num plano perpendicular ?? dire????o da propaga????o dos raios solares no topo da atmosfera terrestre recebe o nome de ???constante solar??? e corresponde ao valor de 1.367 W/m2.");
            resp_correta.add("1.367 W/m2");
            resp_errada.add("529 W/m2");
            resp_errada.add("1145 W/m2");

            perguntas.add("Qual sistema fotovoltaico trabalha com Sistema de compensa????o de energia el??trica?");
            curiosidades.add("Nos sistemas conectados ?? rede, a energia el??trica gerada ?? cedida, por meio de empr??stimo gratuito, ?? distribuidora local e posteriormente compensada com o consumo de energia el??trica ativa dessa mesma unidade consumidora, ou seja, a energia produzida pelo sistema n??o ?? diretamente consumida pelo provedor.");
            resp_correta.add("Sistemas Conectados ?? Rede");
            resp_errada.add("Sistemas isolados");
            resp_errada.add("Sistema de bombeamento");

            perguntas.add("O que acontece no processo de dopagem?");
            curiosidades.add("Calvin Fuller desenvolveu um processo de difus??o para introduzir impurezas em cristais de sil??cio, de modo a controlar as suas propriedades el??tricas, Chamado de dopagem");
            resp_correta.add("Controle de propriedades qu??micas do sil??cio");
            resp_errada.add("Mergulha o sil??cio em ??gua");
            resp_errada.add("Constru????o de uma barra de Sil??cio");

            perguntas.add("A energia solar representa cerca de:");
            curiosidades.add("A energia solar no Brasil representa apenas 1,7% de toda a matriz energ??tica, por??m, o n??mero de sistemas fotovoltaicos instalados no territ??rio brasileiro tem crescido consideravelmente, principalmente, nas regi??es Sul e Sudeste do pa??s.");
            resp_correta.add("1,7% da matriz energ??tica brasileira");
            resp_errada.add("60% da matriz energ??tica");
            resp_errada.add("32% da matriz energ??tica");

            perguntas.add("Qual o principal incentivo de uso da energia solar no Brasil?");
            curiosidades.add("O incentivo chamado de Meetering ?? possibilidade de se injetar na rede el??trica a energia produzida pelos pain??is fotovoltaicos n??o consumida, convert??-la em cr??ditos para a compensa????o posterior, quando o consumo supera a produ????o dos pain??is.");
            resp_correta.add("Meetering");
            resp_errada.add("Grid-tie");
            resp_errada.add("Feed-in tariff");

            perguntas.add("Qual sistema tem como principal caracter??stica o aquecimento de ??gua?");
            curiosidades.add("S??o os sistemas mais simples, econ??micos e conhecidos de aproveitar o sol, sendo utilizados em casas, hot??is e empresas para o aquecimento de ??gua para chuveiros ou piscinas, aquecimentos de ambientes ou at?? em processos industriais.");
            resp_correta.add("Sistema Solar T??rmico");
            resp_errada.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar Fotovoltaico");

            perguntas.add("Qual sistema tem como principal caracter??stica o uso de c??lulas fotovoltaicas?");
            curiosidades.add("Os sistemas fotovoltaicos s??o capazes de gerar energia el??trica atrav??s das chamadas c??lulas fotovoltaicas. As c??lulas fotovoltaicas s??o feitas de materiais capazes de converter a radia????o solar em energia el??trica, atrav??s do chamado ???efeito fotovoltaico???. ");
            resp_correta.add("Sistema Solar Fotovoltaico");
            resp_errada.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar T??rmico");

            perguntas.add("Qual sistema tem como principal caracter??stica a concentra????o da radia????o solar?");
            curiosidades.add("Os sistemas termo solares produzem inicialmente calor, atrav??s de um sistema de espelhos (ou concentradores) que concentram a radia????o solar, e s?? ent??o transformam este calor em energia el??trica.");
            resp_correta.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar Fotovoltaico");
            resp_errada.add("Sistema Solar T??rmico");

            perguntas.add("Quais as duas principais esp??cies b??sicas de sistemas fotovolta??cos?");
            curiosidades.add("Sistemas Isolados (Off-grid) e Sistemas Conectados ?? Rede (On-Grid)");
            resp_correta.add("On-Grid e Off-Grid");
            resp_errada.add("Off-Grid e Oron-Grid");
            resp_errada.add("On-Grid e Oron-Grid");

            perguntas.add("Para qual sistema n??o ?? necess??rio estar conectado a rede el??trica?");
            curiosidades.add("Os Sistemas Isolados (Off-Grid) s??o utilizados em locais remotos ou onde o custo de se conectar a rede el??trica ?? elevado, s??o utilizados em casas de campo, ref??gios, ilumina????o, telecomunica????es, bombeio de ??gua, etc.");
            resp_correta.add("Off-Grid");
            resp_errada.add("Oron-Grid");
            resp_errada.add("On-Grid");

            perguntas.add("Para qual sistema ?? necess??rio estar conectado a rede el??trica?");
            curiosidades.add("Os Sistemas Conectados ?? rede (On-Grid), substituem ou complementam a energia el??trica convencional dispon??vel na rede el??trica.");
            resp_correta.add("On-Grid");
            resp_errada.add("Off-Grid");
            resp_errada.add("Oron-Grid");
        }

        else
        {
            perguntas.add("Qual destes fatores ?? diretamente proporcional ?? corrente el??trica produzida no m??dulo fotovoltaico?");
            curiosidades.add("A corrente el??trica gerada por uma c??lula fotovoltaica aumenta linearmente com o aumento da irradi??ncia solar incidente, enquanto que a tens??o de circuito aberto (Voc) aumenta de forma logar??tmica.");
            resp_correta.add("Irradi??ncia  Solar");
            resp_errada.add("Resist??ncia do m??dulo");
            resp_errada.add("Temperatura");

            perguntas.add("Qual destes componentes de um sistema fotovoltaico s?? ?? necess??rio em sistemas off-grid (independentes)?");
            curiosidades.add("Controladores de carga s??o inclu??dos na maioria dos Sistemas Fotovoltaicos Independentes SFI com o objetivo de proteger a bateria (ou banco de baterias) contra cargas e descargas excessivas, aumentando a sua vida ??til.");
            resp_correta.add("Controladores de carga");
            resp_errada.add("Inversores");
            resp_errada.add("Conversores");

            perguntas.add("Individualmente as c??lulas fotovoltaicas de Sil??cio possuem tens??o na ordem de:");
            curiosidades.add("As c??lulas de Sil??cio possuem, individualmente, uma tens??o muito baixa, sendo da ordem de 0,5 a 0,8V. Assim, para se obterem n??veis de tens??o adequados, as c??lulas s??o conectadas em s??rie, produzindo uma tens??o resultante equivalente ?? soma das tens??es individuais de cada c??lula.");
            resp_correta.add("0,5 a 0,8V");
            resp_errada.add("10 a 15V");
            resp_errada.add("2 a 5V");

            perguntas.add("Seja a tens??o de uma c??lula fotovoltaica 0,6V. Qual a tens??o de um m??dulo com 30 destas c??lulas conectadas em paralelo?");
            curiosidades.add("Na associa????o em paralelo as correntes s??o somadas e a tens??o permanece inalterada, assim, a tens??o do m??dulo ?? a mesma da c??lula. ");
            resp_correta.add("0,6V");
            resp_errada.add("15V");
            resp_errada.add("18V");

            perguntas.add("Seja a tens??o de uma c??lula fotovoltaica 0,6V. Qual a tens??o de um m??dulo com 30 destas c??lulas conectadas em s??rie?");
            curiosidades.add("Na associa????o em s??rie as tens??es s??o somadas e a corrente possui valor ??nico, assim, 30 c??lulas com 0,6V geram uma tens??o de 18V no m??dulo.");
            resp_correta.add("18V");
            resp_errada.add("15V");
            resp_errada.add("0,6V");

            perguntas.add("2 elementos s??o mais comumente utilizados no processo de Dopagem Eletr??nica do Sil??cio, mudando drasticamente suas propriedades el??tricas. Quais s??o eles?");
            curiosidades.add("Os ??tomos de Si possuem 4 el??trons de val??ncia, assim a dopagem pode ser feita de duas maneiras. Introduzindo um ??tomo de F??sforo (Pentavalente) chamado de impureza doadora de el??trons, ou dopante tipo n, ou um ??tomo de Boro (trivalente) denominado impureza recebedora de el??trons ou dopante tipo p.");
            resp_correta.add("Boro e F??sforo");
            resp_errada.add("Ferro e Mangan??s");
            resp_errada.add("Ars??nio e Alum??nio");

            perguntas.add("Qual tipo de bateria ?? mais utilizado nos sistemas fotovoltaicos isolados?");
            curiosidades.add("As c??lulas Chumbo-??cido s??o a tecnologia de armazenamento de energia de menor custo por Wh atualmente dispon??vel no mercado para aplica????o em sistemas fotovoltaicos.");
            resp_correta.add("Chumbo-??cido");
            resp_errada.add("N??quel C??dmio");
            resp_errada.add("L??tio-??on");
        }
    }


}

