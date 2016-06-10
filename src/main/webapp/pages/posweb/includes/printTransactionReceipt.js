function printTransactionReceipt(id, isScheduled) {
    var url;
    if (isReceiptPrinterSet()) {
        if (isScheduled) {
            url = context + "/do/scheduledPaymentReceipt?paymentId=" + id;
        } else {
            url = context + "/do/transactionReceipt?transferId=" + id;
        }
        printReceipt(url);
    } else {
        if (isScheduled) {
            url = context + "/do/printScheduledPayment?print=true&paymentId=" + id;
        } else {
            url = context + "/do/printTransaction?print=true&transferId=" + id;
        }
        printResults(null, url, 500, 300);
    }    
}