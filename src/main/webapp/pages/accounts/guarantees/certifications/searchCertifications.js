Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editCertification";
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editCertification?certificationId=" + img.getAttribute("certificationId");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, context + "/do/exportCertificationsToCsv");
		}
	},

	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, context + "/do/printCertifications");
		}
	},
	
	'#issuerUsername': function(input) {
		var div = $('issuersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, groupIds: issuerGroupIds}, 'issuerId', 'issuerUsername', 'issuerName');
	},
	
	'#issuerName': function(input) {
		var div = $('issuersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, groupIds: issuerGroupIds}, 'issuerId', 'issuerUsername', 'issuerName');
	},
	
	'#buyerUsername': function(input) {
		var div = $('buyersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName');
	},
	
	'#buyerName': function(input) {
		var div = $('buyersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName');
	}
});