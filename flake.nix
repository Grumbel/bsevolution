{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-23.05";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in {
        packages = rec {
          default = bsevolution;

          bsevolution = pkgs.stdenv.mkDerivation rec {
            pname = "bsevolution";
            version = "0.0.0";

            src = ./.;

            buildPhase = ''
              gradle jar
            '';

            installPhase = ''
              mkdir -p $out/bin $out/lib/
              cp -vi build/libs/bsevolution.jar $out/lib/
              echo -e "#!/bin/sh\nexec ${pkgs.jre}/bin/java -jar $out/lib/bsevolution.jar" > $out/bin/bsevolution
              chmod +x $out/bin/bsevolution
            '';

            nativeBuildInputs = with pkgs; [
              gradle
            ];

            buildInputs = with pkgs; [
              openjdk
            ];
          };
        };
      }
    );
}
