


function openDepositPopup() {
    var modal = document.getElementById("depositModal");
    modal.style.display = "block";
}

function openWithdrawPopup() {
    var modal = document.getElementById("withdrawModal");
    modal.style.display = "block";
}

function openTransferPopup() {
    var modal = document.getElementById("transferModal");
    modal.style.display = "block";
}


// Function to close the deposit/withdraw/transfer popup
function closeDepositPopup() {
    var modal = document.getElementById("depositModal");
    modal.style.display = "none";
}

function closeWithdrawPopup() {
    var modal = document.getElementById("withdrawModal");
    modal.style.display = "none";
}

function closeTransferPopup() {
    var modal = document.getElementById("transferModal");
    modal.style.display = "none";
}

function deposit() {
    var depositAmount = parseFloat(document.getElementById("depositAmount").value);

    if (isNaN(depositAmount) || depositAmount <= 0) {
        alert("Please enter a valid positive amount to deposit.");
        return;
    }

    // Assuming customer object is populated in your HTML template
    var acctNum = student.acctNum; // Extract account number from the customer object
    var userId = student.userId;

    // Send the depositAmount and acctNum to the server using AJAX with proper Content-Type
    $.ajax({
        type: "POST",
        url: "/deposit",
        data: JSON.stringify({ acctNum: acctNum, depositAmount: depositAmount }), // Convert object to JSON string
        contentType: "application/json", // Set Content-Type header
        success: function(response) {
            // Update the content of the pop-up window with the response
            document.getElementById("serviceDescription").textContent = response;
            window.location.href = '/cust?userId=' + userId;
        },
        error: function(xhr, status, error) {
            // Handle errors here, if any
            console.error(xhr.responseText);
        }
    })
}

function withdraw() {
    var withdrawAmount = parseFloat(document.getElementById("withdrawAmount").value);
    
    if (isNaN(withdrawAmount) || withdrawAmount <= 0) {
        alert("Please enter a valid positive amount to withdraw.");
        return;
    }

        // Assuming customer object is populated in your HTML template
    var acctNum = student.acctNum; // Extract account number from the customer object
    var userId = student.userId;

        // Check if withdrawal amount is greater than the account balance
    if (withdrawAmount > student.balance) {
        alert("Withdrawal amount cannot exceed the account balance.");
        return;
    }
    
        // Send the withdrawAmount and acctNum to the server using AJAX with proper Content-Type
    $.ajax({
        type: "POST",
        url: "/withdraw",
        data: JSON.stringify({ acctNum: acctNum, withdrawAmount: withdrawAmount }), // Convert object to JSON string
        contentType: "application/json", // Set Content-Type header
        success: function(response) {
                // Update the content of the pop-up window with the response
            document.getElementById("serviceDescription").textContent = response;
            window.location.href = '/cust?userId=' + userId;
        },
        error: function(xhr, status, error) {
                // Handle errors here, if any
            console.error(xhr.responseText);
        }
    })}


function transfer() {
    var transferAmount = parseFloat(document.getElementById("transferAmount").value);
    var receiverAcctNum = document.getElementById("receiverAcctNum").value;

    if (isNaN(transferAmount) || transferAmount <= 0) {
        alert("Please enter a valid positive amount to transfer.");
        return;
    }

    if (!receiverAcctNum) {
        alert("Please enter a valid receiver's account number.");
        return;
    }

    // Validate receiver's account number
    $.ajax({
        type: "GET",
        url: "/validateAccount?acctNum=" + receiverAcctNum,
        contentType: "application/json",
        success: function(isValid) {
            if (isValid) {
                // If the receiver's account number is valid, proceed with the transfer
                initiateTransfer(transferAmount, receiverAcctNum);
            } else {
                alert("Invalid receiver's account number.");
            }
        },
        error: function(xhr, status, error) {
            // Handle errors here, if any
            console.error(xhr.responseText);
        }
    });
}

function initiateTransfer(transferAmount, receiverAcctNum) {
    // Assuming customer object is populated in your HTML template
    var senderAcctNum = student.acctNum; // Extract sender's account number from the customer object
    var userId = student.userId;
    // Send the transferAmount, senderAcctNum, and receiverAcctNum to the server using AJAX with proper Content-Type
    $.ajax({
        type: "POST",
        url: "/transfer",
        data: JSON.stringify({ senderAcctNum: senderAcctNum, receiverAcctNum: receiverAcctNum, transferAmount: transferAmount }), // Convert object to JSON string
        contentType: "application/json", // Set Content-Type header
        success: function(response) {
            // Update the content of the pop-up window with the response
            document.getElementById("serviceDescription").textContent = response;
            window.location.href = '/cust?userId=' + userId;
        },
        error: function(xhr, status, error) {
            // Handle errors here, if any
            console.error(xhr.responseText);
        }
    });
    
}

function openTransactionHistoryPopup() {
    var modal = document.getElementById("transactionHistoryModal");
    modal.style.display = "block";

    // Assuming userId is populated in your HTML template
    var userId = student.userId;

    // Fetch and display transaction history based on userId using AJAX
    $.ajax({
        type: "GET",
        url: "/getTransactionHistory?userId=" + userId,
        contentType: "application/json",
        success: function(transactionHistory) {
            // Assuming transactionHistory is an array of transaction objects received from the server

            // Access the table body element inside the modal
            var tableBody = document.getElementById("transaction-history-table");

            // Clear previous table rows if any
            tableBody.innerHTML = "";

            // Iterate through the transactionHistory and populate the table
            transactionHistory.forEach(function(transaction) {
                var row = tableBody.insertRow();
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);

                // Assuming transaction object has properties like transactionNum, actionPerformed, amount, balanceBefore, balanceAfter, and currdate
                cell1.textContent = transaction.transactionNum;
                cell2.textContent = transaction.actionPerformed;
                cell3.textContent = transaction.amount;
                cell4.textContent = transaction.currdate;
            });
        },
        error: function(xhr, status, error) {
            // Handle errors here, if any
            console.error(xhr.responseText);
        }
    });
}

// Function to close the transaction history modal
function closeTransactionHistoryPopup() {
    var modal = document.getElementById("transactionHistoryModal");
    modal.style.display = "none";
}


