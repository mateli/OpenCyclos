
Behaviour.register({

	'#totalLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedAdsDetails";
		}
	},

	'#totalLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedAdsDetails?importId=" + getValue("importId") + "&status=ALL";
		}
	},

	'#successLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedAdsDetails?importId=" + getValue("importId") + "&status=SUCCESS";
		}
	},

	'#errorLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedAdsDetails?importId=" + getValue("importId") + "&status=ERROR";
		}
	},
	
	'#newCategoriesLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/importedAdCategories?importId=" + getValue("importId");
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/importAds";
		}
	}

});
