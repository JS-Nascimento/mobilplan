package br.dev.jstec.efurniture.application.usecases;

public abstract class UseCase<INPUT, OUTPUT> {

    public abstract OUTPUT execute(INPUT input);
}
