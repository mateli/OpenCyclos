
Behaviour.register({
	'#printLink': function(a) {
		setPointer(a);
		a.onclick = function() {
		    var date = encodeURIComponent(getValue("date"));
            if (isReceiptPrinterSet()) {
                printReceipt(context + "/do/posweb/transactionsReceipt?date=" + date);
            } else {
                printResults(null, context + "/do/posweb/printTransactions?print=true&date=" + date, 500, 300);
            }
		}
	},
	
	'img.print': function(img) {
	    setPointer(img);
	    img.onclick = printTransactionReceipt.bind(self, img.getAttribute("paymentId"), booleanValue(img.getAttribute("isScheduled")));
	}
});

Event.observe(self, "load", function() {
    keyBinding(Event.KEY_F4, $('printLink').onclick);
});