function save() {
    writeCookie("receiptPrinterId", getValue('localPrintSettings'), document, new Date(2099,11,31), context);
    alert(savedMessage);
}

Behaviour.register({
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editReceiptPrinterSettings?id=" + img.getAttribute("printerId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeReceiptPrinterSettings?id=" + img.getAttribute("printerId");
			}
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editReceiptPrinterSettings";
		}
	},
	
	'#localPrintSettings': function(select) {
	    setValue(select, readCookie("receiptPrinterId"));
	},
	
	'#saveReceiptPrintSettingsButton': function(button) {
        button.onclick = save;
	}
});