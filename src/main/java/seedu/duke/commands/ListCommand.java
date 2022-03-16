package seedu.duke.commands;

import static java.util.Objects.requireNonNull;

import seedu.duke.data.Item;
import seedu.duke.data.ItemList;
import seedu.duke.ui.Ui;
import seedu.duke.storage.Storage;

public class ListCommand extends Command {

    public ListCommand() {

    }

    @Override
    public void execute(ItemList itemList, Ui ui, Storage storage) {
        ui.showOutput("Here are the tasks in your list:");
        for (int i = 0; i < itemList.size(); i++) {
            ui.showOutput(String.valueOf(i + 1) + "." + itemList.get(i));
        }
    }
}
