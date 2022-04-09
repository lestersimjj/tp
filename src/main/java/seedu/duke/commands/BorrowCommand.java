package seedu.duke.commands;

import seedu.duke.common.Messages;
import seedu.duke.data.BorrowRecord;
import seedu.duke.data.BorrowStatus;
import seedu.duke.data.Item;
import seedu.duke.data.ItemList;
import seedu.duke.exceptions.InvMgrException;
import seedu.duke.ui.Ui;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Borrow an item from the inventory for a specified duration.
 */
public class BorrowCommand extends Command {
    public static final String COMMAND_WORD = "borrow";
    public static final String COMMAND_NAME = "Borrow an Item";
    public static final String USAGE_MESSAGE = "Borrows an item from the inventory list";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " i/[item number] s/[start date] e/[end date] p/[name]";
    public static final String HELP_MESSAGE = COMMAND_NAME + ":\n" + "[Function] " + USAGE_MESSAGE + ":\n"
            + "[Command Format] " + COMMAND_FORMAT + "\n";

    private final int itemIndex;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String borrowerName;
    private final BorrowStatus borrowStatus;

    public BorrowCommand(int itemIndex, LocalDate startDate, LocalDate endDate, String borrowerName) {
        // itemIndex is parsed in as zero-based indexing.
        this.itemIndex = itemIndex;
        this.startDate = startDate;
        this.endDate = endDate;
        this.borrowerName = borrowerName;

        // Determine the borrow status compared to today's date
        LocalDate today = LocalDate.now();
        if (today.isAfter(this.endDate)) {
            this.borrowStatus = BorrowStatus.PAST;
        } else if (today.isBefore(this.startDate)) {
            this.borrowStatus = BorrowStatus.FUTURE;
        } else {
            this.borrowStatus = BorrowStatus.PRESENT;
        }
    }

    @Override
    public void execute(ItemList itemList, Ui ui) throws InvMgrException {
        // Check if index is within itemList, otherwise throw Exception
        if (itemIndex > itemList.getSize() - 1) {
            throw new InvMgrException(Messages.INVALID_INDEX);
        }

        // Create a new borrow record and add to item
        BorrowRecord newRecord = new BorrowRecord(startDate, endDate, borrowerName, borrowStatus);
        Item item = itemList.addBorrowRecord(itemIndex, newRecord);
        ui.showMessages("Item has been successfully borrowed!",
                "Name of Item: " + item.getName(),
                newRecord.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BorrowCommand that = (BorrowCommand) o;
        return itemIndex == that.itemIndex
                && startDate.equals(that.startDate)
                && endDate.equals(that.endDate)
                && borrowerName.equals(that.borrowerName);
    }

}
