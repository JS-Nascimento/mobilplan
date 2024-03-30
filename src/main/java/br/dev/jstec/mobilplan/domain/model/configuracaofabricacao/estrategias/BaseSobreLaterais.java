package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias;

import static helpers.DescontosPadroes.descontoAlturaFrente;
import static helpers.FechamentosHelper.calcularAlturasDasFrentes;
import static helpers.FechamentosHelper.calcularPortas;

import componentes.PadraoDeFitagem;
import componentes.config.Dimensoes;
import componentes.estruturais.Base;
import componentes.estruturais.ContraFrenteGaveta;
import componentes.estruturais.CorpoGaveta;
import componentes.estruturais.Fundo;
import componentes.estruturais.FundoGaveta;
import componentes.estruturais.Lateral;
import componentes.estruturais.LateralGaveta;
import componentes.estruturais.PrateleiraInterna;
import componentes.estruturais.TraseiroGaveta;
import componentes.estruturais.TravessaHorizontal;
import componentes.fechamentos.ComPuxador;
import componentes.fechamentos.Gaveta;
import componentes.fechamentos.Gaveteiro;
import componentes.fechamentos.Portas;
import helpers.NumberHelper;
import modelos.Gabinete;

public class BaseSobreLaterais implements EstrategiaDeConstrucao {

    @Override
    public void aplicarParaBase(Base base, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem) {
        double largura = dimensoes.getLargura();
        double profundidade = dimensoes.getProfundidade();
        base.setDimensoes(largura, profundidade, dimensoes.getEspessura(), padraoDeFitagem);
    }

    @Override
    public void aplicarParaPrateleiraInterna(PrateleiraInterna prateleiraInterna, Dimensoes dimensoes) {

        var dimensoesInternas = dimensoes.calcularDimensoesInternas();
        double largura = dimensoesInternas.getLargura();
        double profundidade = dimensoesInternas.getProfundidade() - prateleiraInterna.descontoProfundidade();

        prateleiraInterna.setDimensoes(largura, profundidade, dimensoes.getEspessura(),
                prateleiraInterna.getPadraoDeFitagem());
    }

    @Override
    public void aplicarParaPortas(Portas portas, Dimensoes dimensoes) {

        var listaPortas = calcularPortas(portas, dimensoes);

        listaPortas.forEach(portas::adicionarPorta);
    }

    @Override
    public void aplicarParaPuxador(ComPuxador fechamento, Dimensoes dimensoes, Gabinete gabinete) {

        fechamento.getPuxador().ifPresent(puxador -> {
            if (puxador.isPerfil()) {
                switch (puxador.getDirecao()) {
                    case HORIZONTAL -> {
                        var altura = fechamento.altura() - puxador.getDimensoesAcessorio().altura();
                        gabinete.adicionarFerragem(puxador, NumberHelper.mmParaMetros(fechamento.largura()));
                        fechamento.setDimensoes(
                                altura,
                                fechamento.largura(),
                                fechamento.espessura(),
                                fechamento.getPadraoDeFitagem());
                        break;
                    }
                    case VERTICAL -> {
                        var largura = fechamento.largura() - puxador.getDimensoesAcessorio().altura();
                        gabinete.adicionarFerragem(puxador, NumberHelper.mmParaMetros(fechamento.altura()));
                        fechamento.setDimensoes(
                                fechamento.altura(),
                                largura,
                                fechamento.espessura(),
                                fechamento.getPadraoDeFitagem());
                        break;
                    }
                }
            } else {
                gabinete.adicionarFerragem(puxador, 1.0);
                fechamento.setDimensoes(
                        fechamento.altura(),
                        fechamento.largura(),
                        fechamento.espessura(),
                        fechamento.getPadraoDeFitagem());
            }
        });
    }


    @Override
    public void aplicarParaTravessa(TravessaHorizontal travessaHorizontal, Dimensoes dimensoes,
                                    PadraoDeFitagem padraoDeFitagem) {
        double largura = dimensoes.getLargura();
        travessaHorizontal.setDimensoes(largura, dimensoes.getEspessura(), padraoDeFitagem);
    }

    @Override
    public void aplicarParaLateral(Lateral lateral, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem) {

        double altura = dimensoes.getAltura() - 2 * dimensoes.getEspessura();
        double profundidade = dimensoes.getProfundidade();
        lateral.setDimensoes(altura, profundidade, dimensoes.getEspessura(), padraoDeFitagem);
    }

    @Override
    public void aplicarParaFundoGaveta(FundoGaveta fundoGaveta, Dimensoes dimensoes, double espessura,
                                       PadraoDeFitagem padraoDeFitagem) {

    }

    @Override
    public void aplicarParaLateralGaveta(LateralGaveta lateralGaveta, Dimensoes dimensoes,
                                         PadraoDeFitagem padraoDeFitagem) {

        lateralGaveta.setDimensoes(lateralGaveta.altura(), lateralGaveta.profundidade(), lateralGaveta.espessura(),
                padraoDeFitagem);
    }

    @Override
    public void aplicarParaContraFrenteGaveta(ContraFrenteGaveta contraFrenteGaveta, Dimensoes dimensoes,
                                              PadraoDeFitagem padraoDeFitagem) {

        var largura = dimensoes.calcularDimensoesInternas().getLargura()
                - contraFrenteGaveta.getFolgasGavetas().folgaTrilhos()
                - (2 * contraFrenteGaveta.getFolgasGavetas().espessuraCorpo());

        contraFrenteGaveta.setDimensoes(contraFrenteGaveta.altura(), largura,
                contraFrenteGaveta.getFolgasGavetas().espessuraCorpo(), padraoDeFitagem);
    }

    @Override
    public void aplicarParaTraseiroGaveta(TraseiroGaveta traseiroGaveta, Dimensoes dimensoes,
                                          PadraoDeFitagem padraoDeFitagem) {
        var largura = dimensoes.calcularDimensoesInternas().getLargura()
                - traseiroGaveta.getFolgasGavetas().folgaTrilhos()
                - (2 * traseiroGaveta.getFolgasGavetas().espessuraCorpo());

        traseiroGaveta.setDimensoes(traseiroGaveta.altura(), largura,
                traseiroGaveta.getFolgasGavetas().espessuraCorpo(), padraoDeFitagem);

    }

    @Override
    public void aplicarParaGaveta(Gaveteiro gaveteiro, Dimensoes dimensoes) {
        //calcula e valida as alturas das gavetas
        var alturaFrentesCalculadas = calcularAlturasDasFrentes(dimensoes,
                gaveteiro.alturaDeTodasAsFrentes(),
                gaveteiro.quantidadeGavetas(),
                gaveteiro.folgas());

        var largura =
                dimensoes.getLargura() - gaveteiro.folgas().direita() - gaveteiro.folgas().esquerda();

        //adiciona as gavetas calculadas
        alturaFrentesCalculadas.forEach(frente -> {
            var novaGaveta = new Gaveta(
                    descontoAlturaFrente(gaveteiro.tipoFrente(), frente),
                    largura,
                    gaveteiro.espessura(),
                    gaveteiro.tipoFrente(),
                    gaveteiro.folgas(),
                    gaveteiro.folgasGavetas(),
                    gaveteiro.getPadraoDeFitagem(),
                    gaveteiro.getPuxador()
                            .orElseGet(() -> null)
            );
            gaveteiro.adicionarGavetas(novaGaveta);

            //adiciona o corpo da gavetas a cada Gaveta
            novaGaveta.adicionarCorpoGaveta(new CorpoGaveta(dimensoes, gaveteiro.folgasGavetas(),
                    (frente - gaveteiro.folgasGavetas().corpoEmRelacaoFrente())));

            novaGaveta.setDimensoes(novaGaveta.largura(), novaGaveta.altura(), novaGaveta.espessura(),
                    novaGaveta.getPadraoDeFitagem());
        });

    }

    @Override
    public void aplicarParaFundo(Fundo fundo, Dimensoes dimensoes, double espessuraFundo, double valorVariavel,
                                 PadraoDeFitagem padraoDeFitagem) {
        double largura = 0;
        double altura = 0;

        switch (fundo.getTipoFundo()) {
            case SOBREPOSTO:
                largura = dimensoes.getLargura() - 2 * valorVariavel;
                altura = dimensoes.getAltura() - 2 * valorVariavel;
                break;
            case ENCAIXADO:
                largura = (dimensoes.getLargura() - 2 * dimensoes.getEspessura()) + 2 * valorVariavel;
                altura = (dimensoes.getAltura() - 2 * dimensoes.getEspessura()) + 2 * valorVariavel;
                break;
        }
        fundo.setDimensoes(largura, altura, espessuraFundo, padraoDeFitagem);
    }
}
