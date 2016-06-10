Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listAccountFeeLog";
		}
	},
	
	'#rechargeFailedButton': function(button) {
        button.onclick = function() {
            self.location = pathPrefix + "/rechargeFailedAccountFeeLog?accountFeeLogId=" + getValue("accountFeeLogId");
        }
    },
	
    '#memberUsername': function(input) {
        var div = $('membersByUsername');
        prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
    },
    
    '#memberName': function(input) {
        var div = $('membersByName');
        prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
    },

    '.transferLink': function(a) {
        setPointer(a);
        a.onclick = function() {
            self.location = pathPrefix + "/viewTransaction?transferId=" + a.getAttribute("transferId");
        }
    },

    '.invoiceLink': function(a) {
        setPointer(a);
        a.onclick = function() {
            self.location = pathPrefix + "/invoiceDetails?invoiceId=" + a.getAttribute("invoiceId") + "&accountFeeLogId=" + getValue("accountFeeLogId");
        }
    }
});