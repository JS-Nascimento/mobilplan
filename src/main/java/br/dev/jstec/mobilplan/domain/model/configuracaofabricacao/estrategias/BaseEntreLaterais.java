package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias;

import static br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoFundo.ENCAIXADO;
import static br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoFundo.SOBREPOSTO;
import static helpers.FechamentosHelper.calcularAlturasDasFrentes;
import static helpers.FechamentosHelper.calcularPortas;

import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Base;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Fundo;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Lateral;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.PrateleiraInterna;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.TravessaHorizontal;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.TravessaVertical;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.ContraFrenteGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.FundoGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.LateralGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.TraseiroGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import componentes.fechamentos.ComPuxador;
import componentes.fechamentos.Gaveta;
import componentes.fechamentos.Gaveteiro;
import componentes.fechamentos.Portas;
import helpers.NumberHelper;
import modelos.Gabinete;

public class BaseEntreLaterais implements EstrategiaDeConstrucao {

    @Override
    public void aplicarParaBase(Base base, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem) {
        double largura = dimensoes.getLargura() - 2 * dimensoes.getEspessura();
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
    public void aplicarParaTravessaHorizontal(TravessaHorizontal travessaHorizontal, Dimensoes dimensoes,
                                              PadraoDeFitagem padraoDeFitagem) {
        double largura = dimensoes.getLargura() - (2 * dimensoes.getEspessura());
        travessaHorizontal.setDimensoes(largura, dimensoes.getEspessura(), padraoDeFitagem);

    }

    @Override
    public void aplicarParaTravessaVertical(TravessaVertical travessaVertical, Dimensoes dimensoes,
                                            PadraoDeFitagem padraoDeFitagem) {
        double largura = dimensoes.getLargura() - (2 * dimensoes.getEspessura());
        travessaVertical.setDimensoes(largura, dimensoes.getEspessura(), padraoDeFitagem);

    }

    @Override
    public void aplicarParaPortas(Portas portas, Dimensoes dimensoes) {

        var listaPortas = calcularPortas(portas, dimensoes);
        listaPortas.forEach(portas::adicionarPorta);
    }

    @Override
    public void aplicarParaPuxador(ComPuxador fechamento, Dimensoes dimensoes, Gabinete gabinete) {

        fechamento.getPuxador().ifPresent(puxador -> {
            double novaAltura = fechamento.altura();
            double novaLargura = fechamento.largura();

            if (puxador.isPerfil()) {
                switch (puxador.getDirecao()) {
                    case HORIZONTAL:
                        novaAltura -= puxador.getDimensoesAcessorio().altura();
                        gabinete.adicionarFerragem(puxador, NumberHelper.mmParaMetros(fechamento.largura()));
                        break;
                    case VERTICAL:
                        novaLargura -= puxador.getDimensoesAcessorio().altura();
                        gabinete.adicionarFerragem(puxador, NumberHelper.mmParaMetros(fechamento.altura()));
                        break;
                }
            } else {
                gabinete.adicionarFerragem(puxador, 1.0);
            }
            fechamento.setDimensoes(novaAltura, novaLargura, fechamento.espessura(), fechamento.getPadraoDeFitagem());
        });
    }

    @Override
    public void aplicarParaLateral(Lateral lateral, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem) {

        double altura = dimensoes.getAltura();
        double profundidade = dimensoes.getProfundidade();
        lateral.setDimensoes(altura, profundidade, dimensoes.getEspessura(), padraoDeFitagem);

    }

    @Override
    public void aplicarParaFundoGaveta(FundoGaveta fundoGaveta, Dimensoes dimensoes,
                                       double espessura, PadraoDeFitagem padraoDeFitagem) {

        double largura;
        double altura = switch (fundoGaveta.getFolgasGavetas().tipoFundo()) {
            case SOBREPOSTO -> {
                largura = dimensoes.calcularDimensoesInternas().getLargura()
                        - fundoGaveta.getFolgasGavetas().folgaTrilhos()
                        - (2 * fundoGaveta.getFolgasGavetas().espessuraCorpo());
                yield fundoGaveta.getFolgasGavetas().profundidadeGaveta();
            }
            case ENCAIXADO -> {
                largura = dimensoes.calcularDimensoesInternas().getLargura()
                        - fundoGaveta.getFolgasGavetas().folgaTrilhos()
                        - (2 * (fundoGaveta.getFolgasGavetas().espessuraCorpo()))
                        + (2 * fundoGaveta.getFolgasGavetas().rebaixoFundo());
                yield fundoGaveta.getFolgasGavetas().profundidadeGaveta()
                        - (fundoGaveta.getFolgasGavetas().espessuraCorpo() -
                        fundoGaveta.getFolgasGavetas().rebaixoFundo());
            }
        };

        fundoGaveta.setDimensoes(altura, largura, fundoGaveta.getFolgasGavetas().espessuraFundo(), padraoDeFitagem);
    }

    @Override
    public void aplicarParaLateralGaveta(LateralGaveta lateralGaveta, Dimensoes dimensoes,
                                         PadraoDeFitagem padraoDeFitagem) {

        lateralGaveta.setDimensoes(lateralGaveta.altura(), lateralGaveta.profundidade(),
                lateralGaveta.espessura(), padraoDeFitagem);
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
                    largura,
                    frente,
                    gaveteiro.espessura(),
                    gaveteiro.tipoFrente(),
                    gaveteiro.folgas(),
                    gaveteiro.folgasGavetas(),
                    gaveteiro.getPadraoDeFitagem(),
                    gaveteiro.getPuxador().orElseGet(() -> null));
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
        double largura;
        double altura = switch (fundo.getTipoFundo()) {
            case SOBREPOSTO -> {
                largura = dimensoes.getLargura() - 2 * valorVariavel;
                yield dimensoes.getAltura() - 2 * valorVariavel;
            }
            case ENCAIXADO -> {
                largura = (dimensoes.getLargura() - 2 * dimensoes.getEspessura()) + 2 * valorVariavel;
                yield (dimensoes.getAltura() - 2 * dimensoes.getEspessura()) + 2 * valorVariavel;
            }
        };

        fundo.setDimensoes(largura, altura, espessuraFundo, padraoDeFitagem);
    }

}
