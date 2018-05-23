package betterwithmods.api.recipe;

public interface IMatcher<T, I extends IMatchInfo> {

    T matches(I info);

}
