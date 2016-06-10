Behaviour.register({
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printPendingMembers");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportPendingMembersToCsv");
		}
	},
	
	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'.edit': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/pendingMemberProfile?pendingMemberId=" + element.getAttribute("pendingMemberId");
		}
	},
	
	'.remove': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removePendingMember?pendingMemberId=" + element.getAttribute("pendingMemberId");
			}
		}
	}
});

Event.observe(self, "load", function() {
	getObject("query(name)").focus();
});