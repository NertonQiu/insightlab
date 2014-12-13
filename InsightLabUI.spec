Summary:%{rpmname}
Name:  %{rpmname}
Version: %{rpmver}
Release: %{rpmrelease}

%define _rpmfilename %%{ARCH}/%%{NAME}-%%{VERSION}-%%{RELEASE}.%{ARCH}.rpm

License: Acxiom Corporation, 2013
Group: Insight Lab
Source: %{name}-%{version}.tar.gz
URL:  https://acx.corp.acxiom.net/sf/projects/insight_lab/
Distribution: Insight lab
Vendor: Acxiom Corporation
Packager: Phillip Langley <phillip.langley@acxiom.com>
Provides: %{name}
Prefix: /usr/local
Autoreq: no	
BuildRoot: %{_tmppath}/%{name}-buildroot/
BuildArch: x86_64

Obsoletes: insightlab-web
provides: insightlab-web


%description
This packages Insight Lab Web war into rpm

%prep
%setup -q		

%build
mvn clean package install -Dbuild.number=${BUILD_NUMBER}

%install		
mkdir -p ${RPM_BUILD_ROOT}/usr/java/tomcat/webapps/
mkdir -p ${RPM_BUILD_ROOT}/product/services/InsightLabUI/
cp insightlab-services-client/target/insightlab.war ${RPM_BUILD_ROOT}/usr/java/tomcat/webapps/
cp conf/Configuration.xml ${RPM_BUILD_ROOT}/product/services/InsightLabUI/


%clean
rm -rf ${RPM_BUILD_ROOT}
cd ${RPM_BUILD_DIR}
rm -rf ${RPM_PACKAGE_NAME}-${RPM_PACKAGE_VERSION}

%files
%defattr(-,root,root)
/usr/java/tomcat/webapps/*.war
/product/services/InsightLabUI/*.xml
