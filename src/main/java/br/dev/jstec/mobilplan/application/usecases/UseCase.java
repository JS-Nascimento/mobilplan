package br.dev.jstec.mobilplan.application.usecases;

public abstract class UseCase<INPUT, OUTPUT> {

    public abstract OUTPUT execute(INPUT input);
}
