package github.chorman0773.sentry.test;

import github.chorman0773.sentry.GameBasic;
import github.chorman0773.sentry.annotation.Game;
import github.chorman0773.sentry.core.service.LauncherTerminal;

@Game(gameId = "test-game", uuid = "014fe95e-37df-11ea-aec2-2e728ce88125", version = "1.0")
public final class TestGame extends GameBasic {
    public TestGame() {}

    @Override
    public void start() {
        this.getLauncherInterface().getService(LauncherTerminal.class)
                .ifPresent(s->s.getTerminalStream().println("TestGame#start"));
    }

    @Override
    public void stop() {
        this.getLauncherInterface().getService(LauncherTerminal.class)
                .ifPresent(s->s.getTerminalStream().println("TestGame#stop"));
    }

    @Override
    public void run() {

    }
}
