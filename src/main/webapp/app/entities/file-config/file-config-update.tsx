import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './file-config.reducer';
import { IFileConfig } from 'app/shared/model/file-config.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FileConfigUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const fileConfigEntity = useAppSelector(state => state.fileConfig.entity);
  const loading = useAppSelector(state => state.fileConfig.loading);
  const updating = useAppSelector(state => state.fileConfig.updating);
  const updateSuccess = useAppSelector(state => state.fileConfig.updateSuccess);
  const handleClose = () => {
    props.history.push('/file-config');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fileConfigEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...fileConfigEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.fileConfig.home.createOrEditLabel" data-cy="FileConfigCreateUpdateHeading">
            <Translate contentKey="akountsApp.fileConfig.home.createOrEditLabel">Create or edit a FileConfig</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="file-config-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.fileConfig.filename')}
                id="file-config-filename"
                name="filename"
                data-cy="filename"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.description')}
                id="file-config-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.amount')}
                id="file-config-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.amountIn')}
                id="file-config-amountIn"
                name="amountIn"
                data-cy="amountIn"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.amountOut')}
                id="file-config-amountOut"
                name="amountOut"
                data-cy="amountOut"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.accountNum')}
                id="file-config-accountNum"
                name="accountNum"
                data-cy="accountNum"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.transactionDate')}
                id="file-config-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.dateFormat')}
                id="file-config-dateFormat"
                name="dateFormat"
                data-cy="dateFormat"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.fieldSeparator')}
                id="file-config-fieldSeparator"
                name="fieldSeparator"
                data-cy="fieldSeparator"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.hasHeader')}
                id="file-config-hasHeader"
                name="hasHeader"
                data-cy="hasHeader"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.note')}
                id="file-config-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.locale')}
                id="file-config-locale"
                name="locale"
                data-cy="locale"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.multipleAccount')}
                id="file-config-multipleAccount"
                name="multipleAccount"
                data-cy="multipleAccount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.encoding')}
                id="file-config-encoding"
                name="encoding"
                data-cy="encoding"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.fileConfig.sign')}
                id="file-config-sign"
                name="sign"
                data-cy="sign"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/file-config" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FileConfigUpdate;
