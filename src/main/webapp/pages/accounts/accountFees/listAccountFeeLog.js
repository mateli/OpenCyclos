Behaviour.register({

	'a.runFee': function(a) {
		setPointer(a);
		a.style.textDecoration = "underline";
		a.onclick = function() {
			if (confirm(runConfirmation)) {
				self.location = pathPrefix + "/runAccountFee?accountFeeId=" + a.getAttribute("accountFeeId");
			}
		}
	},
	
	'img.view': function(img) {
	    setPointer(img);
	    img.onclick = function() {
	        self.location = pathPrefix + "/viewAccountFeeLogDetails?accountFeeLogId=" + img.getAttribute("accountFeeLogId");
	    }
	}
});
