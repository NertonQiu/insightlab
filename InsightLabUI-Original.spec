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

%description
This packages Insight Lab Web war into rpm

%prep
%setup -q		

%build
mvn clean package install -DskipTests -P production

%install		
mkdir -p ${RPM_BUILD_ROOT}/usr/share/tomcat6/webapps/
cp insightlab-services-client/target/insightlab.war ${RPM_BUILD_ROOT}/usr/share/tomcat6/webapps/


%clean
rm -rf ${RPM_BUILD_ROOT}
cd ${RPM_BUILD_DIR}
rm -rf ${RPM_PACKAGE_NAME}-${RPM_PACKAGE_VERSION}

%files
%defattr(-,root,root)
/usr/share/tomcat6/webapps/*.war
